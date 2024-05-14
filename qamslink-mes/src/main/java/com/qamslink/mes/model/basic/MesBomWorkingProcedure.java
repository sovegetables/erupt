package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_bom_working_procedure")
@Getter
@Setter
@Erupt(name = "bom工序", orderBy = "MesBomWorkingProcedure.sort asc")
public class MesBomWorkingProcedure extends BaseModel {

    @ManyToOne
    @EruptField(
            views = @View(title = "工序", column = "name"),
            edit = @Edit(title = "工序", type = EditType.REFERENCE_TABLE, notNull = true)
    )
    private MesWorkingProcedure workingProcedure;

    @EruptField(
            views = @View(title = "排序"),
            edit = @Edit(title = "排序", numberType = @NumberType(min = 1, step = 1))
    )
    private Integer sort = 0;

    @EruptField(
            views = @View(title = "检测类型"),
            edit = @Edit(title = "检测类型", type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "报工前检测",value = "1"),
                            @VL(label = "报工后检测",value = "2")
                    }
            ))
    )
    private Integer workEndInspectionType = 1;
}
