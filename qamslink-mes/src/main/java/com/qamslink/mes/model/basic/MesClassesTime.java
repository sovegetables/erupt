package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Setter
@Getter
@Entity
@Table(name = "aps_classes_time")
@Erupt(name = "班次时间段")
public class MesClassesTime extends BaseModel {

    @Transient
    private Long classesId;

    @EruptField(
            views = @View(title = "开始时间"),
            edit = @Edit(title = "开始时间", notNull = true, type = EditType.DATE, dateType = @DateType(type = DateType.Type.TIME))
    )
    private String startTime;

    @EruptField(
            views = @View(title = "结束时间"),
            edit = @Edit(title = "结束时间", notNull = true,  type = EditType.DATE, dateType = @DateType(type = DateType.Type.TIME))
    )
    private String endTime;

}
