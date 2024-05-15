package com.qamslink.mes.model.supplier;

import com.qamslink.mes.core.OrderCodeGenerator;
import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.production.PurOrder;
import com.qamslink.mes.model.production.PurOrderStock;
import com.qamslink.mes.model.warehouse.MesStockBarcodePrintDetail;
import com.qamslink.mes.repository.MesPurOrderStockRepository;
import com.qamslink.mes.type.BarCodeType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.ChoiceFetchHandler;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.fun.VLModel;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.InputType;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.core.exception.EruptApiErrorTip;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.qamslink.mes.model.warehouse.MesStockBarcodePrintDetail.STATUS_OUT;

@Entity
@Getter
@Setter
@Table(name ="mes_material_batch_barcode")
@Erupt(name = "供应商生成物料条码",
        power = @Power(delete = false, edit = false, query = false, addBtnName = "生成物料条码"),
        dataProxy = PurMaterialGenerator.InnerDataProxy.class
)
public class PurMaterialGenerator extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "采购单号", column = "code", show = false),
                    @View(title = "供应商名称", column = "supplier_name", show = false),
                    @View(title = "供应商编码", column = "supplier_code", show = false),
            },
            edit = @Edit(title = "采购单号",
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"),
                    notNull = true))
    private PurOrder purOrder;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料编码", column = "stock.code", show = false),
                    @View(title = "stockId", column = "stock.id", show = false),
            },
            edit = @Edit(title = "物料编码", type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "stock"),
                    notNull = true)
    )
    @Transient
    private PurOrderStock purOrderStock;

    @ManyToOne
    private MesStock mesStock;

    @EruptField(edit = @Edit(title = "本次送货总数", notNull = true, numberType = @NumberType(min = 0, step = 1)))
    private BigDecimal total;

    @EruptField(edit = @Edit(title = "最小包装数量", notNull = true, numberType = @NumberType(min = 0, step = 1)))
    private BigDecimal minPackageNum;

    @EruptField(edit = @Edit(title = "生产日期", type = EditType.DATE, notNull = true))
    private Date productionDate;

    @EruptField(edit = @Edit(title = "批次号"))
    private String batchNumber;

    @Transient
    @EruptField(edit = @Edit(title = "供应商名称", readonly = @Readonly))
    private String supplierName;

    @Transient
    @EruptField(edit = @Edit(title = "供应商编码", readonly = @Readonly))
    private String supplierCode;

    @EruptField(edit = @Edit(title = "订单总数量", readonly = @Readonly,
            numberType = @NumberType(min = 0, step = 1)))
    private BigDecimal orderTotal;

    @EruptField(edit = @Edit(title = "已打包数量", readonly = @Readonly,
            numberType = @NumberType(min = 0, step = 1)))
    private BigDecimal packagedNum;

    @Transient
    @EruptField(edit = @Edit(title = "产品规格", readonly = @Readonly))
    private String stockSpec;

    @EruptField(edit = @Edit(title = "备注"))
    private String remark;

    @EruptField(
            edit = @Edit(
                    title = "打印机名称",
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(fetchHandler = InnerChoiceFetch.class),
                    inputType = @InputType(fullSpan = true)
            )
    )
    private String printName;

    public static class InnerChoiceFetch implements ChoiceFetchHandler {

        @Override
        public List<VLModel> fetch(String[] params) {
            return new ArrayList<>();
        }

    }

    @JoinColumn(name = "pur_batch_barcode_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = {
                    @View(title = "条码", column = "barcodeNum"),
                    @View(title = "数量", column = "capacity"),
                    @View(title = "物料编码", column = "stock_code"),
                    @View(title = "物料名称", column = "stock_name"),
            },
            edit = @Edit(title = "物料明细明细", type = EditType.TAB_TABLE_ADD, readonly = @Readonly)
    )
    private List<MesStockBarcodePrintDetail> details;

    @Service
    public static class InnerDataProxy implements DataProxy<PurMaterialGenerator>{

        @Autowired
        private OrderCodeGenerator codeGenerator;
        @Autowired
        private JPAQueryFactory jpaQueryFactory;
        @Autowired
        private MesPurOrderStockRepository purOrderStockRepository;

        @Override
        public void beforeAdd(PurMaterialGenerator purMaterialGenerator) {
            BigDecimal total = purMaterialGenerator.getTotal();
            if(total.compareTo(BigDecimal.ZERO) <= 0){
                throw new EruptApiErrorTip("本次送货总数必须大于0");
            }
            BigDecimal minPackageNum = purMaterialGenerator.getMinPackageNum();
            if(minPackageNum.compareTo(BigDecimal.ZERO) <= 0){
                throw new EruptApiErrorTip("最小包装数量必须大于0");
            }
            BigDecimal remainder = total.remainder(minPackageNum);
            int divide = total.divide(minPackageNum, RoundingMode.DOWN).intValue();
            int count = divide == 0? divide: divide + 1;
            ArrayList<MesStockBarcodePrintDetail> printDetails = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                MesStockBarcodePrintDetail printDetail = new MesStockBarcodePrintDetail();
                String barCode = codeGenerator.generateBarCode(BarCodeType.STOCK_CODE);
                if(i == count - 1 && remainder.compareTo(BigDecimal.ZERO) > 0){
                    printDetail.setCapacity(remainder);
                }else {
                    printDetail.setCapacity(minPackageNum);
                }
                printDetail.setBarcodeNum(barCode);
                printDetail.setType(BarCodeType.STOCK_CODE);
                printDetail.setBarcodeStatus(true);
                printDetail.setAvailableQuantity(printDetail.getCapacity());
                PurOrderStock orderStock = purMaterialGenerator.getPurOrderStock();
                MesStock stock = purOrderStockRepository.findById(orderStock.getId()).get().getStock();
                printDetail.setStock(stock);
                printDetail.setStatus(STATUS_OUT);
                printDetails.add(printDetail);
            }
            purMaterialGenerator.setDetails(printDetails);
        }

        @Override
        public void afterAdd(PurMaterialGenerator purMaterialGenerator) {
            DataProxy.super.afterAdd(purMaterialGenerator);
        }
    }
}
