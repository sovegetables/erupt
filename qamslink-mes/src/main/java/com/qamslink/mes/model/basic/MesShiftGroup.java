package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_group")
@Setter
@Getter
@Erupt(name = "班组信息")
public class MesShiftGroup extends BaseModel {

    @EruptField(
            views = @View(title = "班组名称"),
            edit = @Edit(title = "班组名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @ManyToOne
    @EruptField(
            views = @View(title = "班组负责人", column = "name"),
            edit = @Edit(title = "班组负责人", type = EditType.REFERENCE_TABLE, notNull = true, search = @Search(vague = true))
    )
    private EruptUser manager;


}
