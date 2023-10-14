package com.qamslink.mes.model.equipment;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Setter
@Getter
@Table(name = "mes_equipment_run_condition")
@Erupt(name = "设备运行情况",
        orderBy = "MesEquipmentRunCondition.nowStartTime desc",
//        dataProxy = MesEquipmentRunConditionService.class,
        filter = @Filter(value = "MesEquipmentRunCondition.tenantId",
                conditionHandler = TenantFilter.class,
                params = {"and MesEquipmentRunCondition.deleted = false"}
        ),
        power = @Power(add = false,delete = false)
)
@SQLDelete(sql = "update mes_equipment_run_condition set deleted = true where id = ?")
public class MesEquipmentRunCondition extends HyperModelCreatorVo{

    @EruptField(
            views = @View(title = "设备名称"),
            edit = @Edit(title = "设备名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "设备编号"),
            edit = @Edit(title = "设备编号", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @EruptField(
            views = @View(title = "模具名称"),
            edit = @Edit(title = "模具名称", notNull = true,search = @Search(vague = true))
    )
    private String mouldName;

    @EruptField(
            views = @View(title = "模具编码"),
            edit = @Edit(title = "模具编码", notNull = true,search = @Search(vague = true))
    )
    private String mouldCode;

    @EruptField(
            views = @View(title = "工单编号"),
            edit = @Edit(title = "工单编号", notNull = true,search = @Search(vague = true))
    )
    private String wordOrderCode;

    @EruptField(
            views = @View(title = "任务编号"),
            edit = @Edit(title = "任务编号", notNull = true,search = @Search(vague = true))
    )
    private String taskCode;

    @EruptField(
            views = @View(title = "计划开始时间"),
            edit = @Edit(title = "计划开始时间", notNull = true,type = EditType.DATE)
    )
    private Date startTime;

    @EruptField(
            views = @View(title = "计划结束时间"),
            edit = @Edit(title = "计划结束时间", notNull = true,type = EditType.DATE)
    )
    private Date endTime;

    @EruptField(
            views = @View(title = "实际开始时间"),
            edit = @Edit(title = "实际开始时间",show = false,type = EditType.DATE)
    )
    private Date nowStartTime;

    @EruptField(
            views = @View(title = "实际结束时间"),
            edit = @Edit(title = "实际结束时间",show = false,type = EditType.DATE)
    )
    private Date nowEndTime;


    //工单ID
    private Long workOrderId;

    //任务ID
    private Long taskId;

    //设备ID
    private Long equipmentId;

    //工位ID
    private Long resourceId;

    @EruptField
    private Long tenantId;

    private Boolean deleted = false;
}
