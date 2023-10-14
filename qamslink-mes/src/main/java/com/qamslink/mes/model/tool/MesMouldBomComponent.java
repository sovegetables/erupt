package com.qamslink.mes.model.tool;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_mould_bom_component")
@Setter
@Getter
@Erupt(name = "工具BOM与工具关联")
public class MesMouldBomComponent extends BaseModel {

    @ManyToOne
    @EruptField(
            views = @View(title = "构件名称", column = "name"),
            edit = @Edit(title = "构件名称", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesMouldComponent mouldComponent;

    @EruptField(
            views = @View(title = "构件描述"),
            edit = @Edit(title = "构件描述", type = EditType.TEXTAREA)
    )
    private String description;
}
