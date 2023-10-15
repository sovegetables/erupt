package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.equipment.MesEquipment;
import com.qamslink.mes.model.production.MesProductionScheduleDetail;
import com.qamslink.mes.model.production.MesWorkOrder;
import com.qamslink.mes.model.tool.MesMould;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.*;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "mes_product_batch_printer")
@Erupt(name = "产品批次条码记录",
//        dataProxy = MesProductBatchPrinterService.class,
        orderBy = "MesProductBatchPrinter.createTime desc",
        filter = @Filter(value = "MesProductBatchPrinter.tenantId",
                params = {"and MesProductBatchPrinter.deleted = false"},
                conditionHandler = TenantFilter.class),
        power = @Power(add = false, delete = false))
@SQLDelete(sql = "update mes_product_batch_printer set deleted = true where id = ?")
public class MesProductBatchPrinter extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views = @View(title = "生产工单", column = "orderCode"),
            edit = @Edit(title = "生产工单", notNull = true, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @ManyToOne
    @EruptField(
            views = @View(title = "生产任务", column = "code"),
            edit = @Edit(title = "生产任务", notNull = true, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesProductionScheduleDetail productionScheduleDetail;

    @ManyToOne
    @EruptField(
            views = @View(title = "设备", column = "name"),
            edit = @Edit(title = "设备", notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesEquipment equipment;

    @ManyToOne
    @EruptField(
            views = @View(title = "模具", column = "name"),
            edit = @Edit(title = "模具", notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesMould mould;

    @ManyToOne
    @EruptField(
            views = @View(title = "产品", column = "name"),
            edit = @Edit(title = "产品", notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "进行中", value = "0"),
                            @VL(label = "已完成", value = "1")
                    }
            ))
    )
    private Integer status;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @EruptField(
//            views = @View(title = "总数量"),
            edit = @Edit(title = "总数量", notNull = true)
    )
    private BigDecimal num;

    @EruptField(
//            views = @View(title = "单位条码数"),
            edit = @Edit(title = "单位条码数", notNull = true)
    )
    private BigDecimal unitNum;

    @EruptField(
            views = @View(title = "完成时间"),
            edit = @Edit(title = "完成时间",notNull = true, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date finishTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_batch_printer_id")
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(title = "详情", type = EditType.TAB_TABLE_ADD , readonly = @Readonly()
            )
    )
    private List<MesProductBatchPrinterDetail> productBatchPrinterDetails;

    @Transient
    @EruptField(
            views = @View(title = "打印", type = ViewType.LINK)
    )
    private String url;

    

    private Boolean deleted = false;
}
