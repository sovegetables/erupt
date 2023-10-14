package com.qamslink.mes.model.production;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "mes_start_work_stop_resource")
@Setter
@Getter
@Erupt(name = "开工暂停恢复记录",
//        dataProxy = MesStartWorkStopResourceService.class,
        orderBy = "MesStartWorkStopResource.stopTime desc",
        filter = @Filter(
                value = "MesStartWorkStopResource.tenantId",
                conditionHandler = TenantFilter.class
        ),
        power = @Power(add = false, edit = false, delete = false)
)
public class MesStartWorkStopResource extends BaseModel {
    @ManyToOne
    @EruptField(
            views = {@View(title = "任务编号", column = "code")},
            edit = @Edit(title = "任务编号", notNull = true, search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesProductionScheduleDetail task;

    @ManyToOne
    @EruptField(
            views = {@View(title = "暂停员工", column = "account"),
                    @View(title = "暂停员工号", column = "empno")},
            edit = @Edit(title = "暂停员工", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private EruptUser user;

    @ManyToOne
    @EruptField(
            views = {@View(title = "恢复员工", column = "account"),
                    @View(title = "恢复员工号", column = "empno")},
            edit = @Edit(title = "恢复员工", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private EruptUser userNo;

    @EruptField(
            views =@View(title = "现在状态"),
            edit = @Edit(title = "现在状态", notNull = true, search = @Search(vague = true),
                    type = EditType.BOOLEAN,boolType = @BoolType(trueText = "已恢复", falseText = "已暂停")
    ))
    private Boolean nowStatus = false;

    @EruptField(
            views = {@View(title = "暂停时长")},
            edit = @Edit(title = "暂停时长", notNull = true, editShow = false)
    )
    private String pauseDuration;

    @EruptField(
            views = {@View(title = "暂停时间")},
            edit = @Edit(title = "暂停时间", notNull = true, editShow = false , readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date stopTime;

    @EruptField(
            views = {@View(title = "恢复时间")},
            edit = @Edit(title = "恢复时间", notNull = true, editShow = false , readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date resumeTime;

    @EruptField(
            edit = @Edit(title = "租户", readonly = @Readonly, show = false)
    )
    private Long tenantId;

    @Transient
    private Long userId;
    @Transient
    private Long userNoId;
    @Transient
    private Long taskId;
}
