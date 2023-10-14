package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesGroupUser;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "mes_production_schedule_user")
@Setter
@Getter
@Erupt(name = "排产任务人员"
//        ,
//        dataProxy = MesProductionScheduleUserService.class
)
public class MesProductionScheduleUser extends BaseModel {

    @ManyToOne
    @EruptField(
            views = @View(title = "排产人员", column = "name"),
            edit = @Edit(title = "排产人员", notNull = true,
                    readonly = @Readonly(),
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "name"))
    )
    private MesGroupUser user;

    @Transient
    private Long taskId;

    @Transient
    private Long userId;
}
