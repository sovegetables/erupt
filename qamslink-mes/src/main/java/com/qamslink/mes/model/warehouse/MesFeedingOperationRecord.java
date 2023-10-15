package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesResource;
import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.production.MesProductionScheduleDetail;
import com.qamslink.mes.model.production.MesWorkOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "mes_feeding_operation_record")
@Erupt(name = "生产领料单",
//        dataProxy = MesFeedingOperationRecordService.class,
        orderBy = "MesFeedingOperationRecord.createTime desc",
        power = @Power(add = false, delete = false, edit = false),
        filter = @Filter(value = "MesFeedingOperationRecord.tenantId",
                params = {"and MesFeedingOperationRecord.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_feeding_operation_record set deleted = true where id = ?")
public class MesFeedingOperationRecord extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "领料单号"),
            edit = @Edit(title = "领料单号", notNull = true, search = @Search(vague = true))
    )
    private String code;

    public static final int TYPE_DOWN = 1;
    public static final int TYPE_UP = 2;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", notNull = true, type = EditType.CHOICE,search = @Search(vague = false),
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "倒冲领料", value = TYPE_DOWN + ""),
                            @VL(label = "普通领料", value = TYPE_UP + "")
                    }))
    )
    private Integer type = 1;

    @ManyToOne
    @EruptField(
            views = {@View(title = "生产工单", column = "orderCode")},
            edit = @Edit(title = "生产工单", notNull = true, search = @Search(vague = true),
                    readonly = @Readonly(),
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @ManyToOne
    @EruptField(
            views = @View(title = "生产任务", column = "code"),
            edit = @Edit(title = "生产任务", search = @Search(vague = true), notNull = true, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesProductionScheduleDetail productionScheduleDetail;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "产品名称", column = "name"),
                    @View(title = "产品编码", column = "code")
            },
            edit = @Edit(title = "产品", search = @Search(vague = true), notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = @View(title = "工位", column = "name"),
            edit = @Edit(title = "工位",  search = @Search(vague = true), type = EditType.REFERENCE_TABLE, notNull = true)
    )
    private MesResource resource;

    @EruptField(
            views = @View(title = "产出数量"),
            edit = @Edit(title = "产出数量",readonly = @Readonly(), numberType = @NumberType(min = 1))
    )
    private BigDecimal capacity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "feeding_operation_record_id")
    @EruptField(
            views = @View(title = "物料详情"),
            edit = @Edit(title = "物料详情", type = EditType.TAB_TABLE_ADD, readonly = @Readonly()
            )
    )
    private List<MesFeedingOperationRecordDetail> feedingOperationRecordDetails;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    

    private Boolean deleted = false;
}
