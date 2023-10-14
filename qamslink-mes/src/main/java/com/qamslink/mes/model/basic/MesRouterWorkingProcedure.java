package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name ="mes_router_procedure")
@Erupt(name = "路由工序")
@Getter
@Setter
public class MesRouterWorkingProcedure extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {@View(title = "工序id", column = "name")},
            edit = @Edit(title = "工序id", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesWorkingProcedure workingProcedure;

    @ManyToOne
    @EruptField(
            views = @View(title = "路由id", column = "name")
    )
    private MesRouter router;

    @EruptField(
            views = @View(title = "工序图id")
    )
    private String processId;

    @EruptField(
            views = @View(title = "质检流程"),
            edit = @Edit(title = "质检流程", type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "先检验再打码", value = "1"),
                                    @VL(label = "先打码再检验", value = "2")
                            }
                    ))
    )
    private Integer inspectionProcess;
}
