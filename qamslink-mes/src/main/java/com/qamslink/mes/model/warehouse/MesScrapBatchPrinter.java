package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.production.MesWorkOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.*;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "mes_scrap_batch_printer")
@Erupt(name = "废料条码记录",
//        dataProxy = MesScrapBatchPrinterService.class,
        orderBy = "MesScrapBatchPrinter.createTime desc",
        filter = @Filter(value = "MesScrapBatchPrinter.tenantId",
                params = {"and MesScrapBatchPrinter.deleted = false"},
                conditionHandler = TenantFilter.class),
        power = @Power(add = false, delete = false))
@SQLDelete(sql = "update mes_scrap_batch_printer set deleted = true where id = ?")
public class MesScrapBatchPrinter extends HyperModelCreatorVo {

    @ManyToOne
    @EruptField(
            views = @View(title = "生产工单", column = "orderCode"),
            edit = @Edit(title = "生产工单", notNull = true, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @ManyToOne
    @EruptField(
            views = @View(title = "产品", column = "name"),
            edit = @Edit(title = "产品", notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", type = EditType.CHOICE,
                    readonly = @Readonly(),
                    choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "未入库", value = "0"),
                            @VL(label = "已入库", value = "1")
                    }
            ))
    )
    private Integer status = 1;

    @EruptField(
            views = @View(title = "打码总数量"),
            edit = @Edit(title = "打码总数量", notNull = true, numberType = @NumberType(min = 1))
    )
    private BigDecimal sumTotal;

    @EruptField(
            views = @View(title = "单位包装数量"),
            edit = @Edit(title = "单位包装数量", notNull = true, numberType = @NumberType(min = 1))
    )
    private BigDecimal unitNum;

    @EruptField(
            views = @View(title = "生产日期"),
            edit = @Edit(title = "生产日期")
    )
    private Date manufactureDate;

    @EruptField(
            views = @View(title = "打印机名称")
    )
    private String printerName;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "scrap_batch_printer_id")
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(title = "详情", type = EditType.TAB_TABLE_ADD , readonly = @Readonly()
            )
    )
    private List<MesScrapBatchPrintDetail> scrapBatchPrintDetails;

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
