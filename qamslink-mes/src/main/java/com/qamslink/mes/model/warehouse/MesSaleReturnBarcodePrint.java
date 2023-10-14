package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesPrinter;
import com.qamslink.mes.model.basic.MesStock;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.*;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mes_sale_return_barcode_print")
@Getter
@Setter
@Erupt(name = "销售退货单物料条码打印",
//        dataProxy = MesSaleReturnBarcodePrintService.class,
        orderBy = "MesSaleReturnBarcodePrint.createTime desc",
        filter = @Filter(value = "MesSaleReturnBarcodePrint.tenantId",
                params = {"and MesSaleReturnBarcodePrint.deleted = false"},
                conditionHandler = TenantFilter.class),
        power = @Power(add = false, delete = false))
@SQLDelete(sql = "update mes_sale_return_barcode_print set deleted = true where id = ?")
public class MesSaleReturnBarcodePrint extends HyperModelCreatorVo {

    @ManyToOne
    @EruptField(
            views = @View(title = "销售退货单", column = "code"),
            edit = @Edit(title = "销售退货单", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "id"))
    )
    private MesSaleReturnRecord mesSaleReturnRecord;

    @ManyToOne
    @EruptField(
            views = @View(title = "物料", column = "name"),
            edit = @Edit(title = "物料", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(dependField = "stock_id"))
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "物料数量"),
            edit = @Edit(title = "物料数量", notNull = true, numberType = @NumberType(min = 1))
    )
    private BigDecimal stockQuantity;

    @EruptField(
            views = @View(title = "单位条码数"),
            edit = @Edit(title = "单位条码数", notNull = true, numberType = @NumberType(min = 1))
    )
    private BigDecimal unitNum;

    @EruptField(
            views = @View(title = "已打数量")
    )
    private Integer printNum;

    @EruptField(
            views = @View(title = "批次号"),
            edit = @Edit(title = "批次号")
    )
    private String batchNumber;

    @EruptField(
            views = @View(title = "生产日期"),
            edit = @Edit(title = "生产日期")
    )
    private Date manufactureDate;

    @Transient
    @ManyToOne
    @EruptField(
            edit = @Edit(title = "打印机名称", type = EditType.REFERENCE_TABLE)
    )
    private MesPrinter printer;

    @EruptField(
            views = @View(title = "打印机名称")
    )
    private String printerName;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注")
    )
    private String remark;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sale_return_barcode_print_id")
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(title = "详情", type = EditType.TAB_TABLE_ADD, readonly = @Readonly())
    )
    private List<MesSaleReturnBarcodePrintDetail> saleReturnBarcodePrintDetails;

    @Transient
    @EruptField(
            views = @View(title = "打印", type = ViewType.LINK)
    )
    private String url;

    @EruptField(
            views = @View(title = "租户", show = false)
    )
    private Long tenantId;

    private Boolean deleted = false;
}
