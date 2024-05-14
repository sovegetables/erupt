package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.Tree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mes_workshop", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Getter
@Setter
@Erupt(name = "车间管理", tree = @Tree(pid = "id"),power = @Power(importable = true))
public class MesWorkshop extends HyperModelVo {

    @EruptField(
            views = @View(title = "车间编码", highlight = true, sortable = true),
            edit = @Edit(title = "车间编码", notNull = true,
                    placeHolder = "保存时自动生成",
                    search = @Search(vague = true))
    )
    @CodeGenerator
    private String code;

    @EruptField(
            views = @View(title = "车间名称"),
            edit = @Edit(title = "车间名称", notNull = true, search = @Search(vague = true))
    )
    private String name;
    @ManyToOne
    @EruptField(
            views = @View(title = "负责人", column = "name"),
            edit = @Edit(title = "负责人",
                    type = EditType.REFERENCE_TABLE,
                    search = @Search(vague = true))
    )
    private EruptUser leaderUser;
    @EruptField(
            views = @View(title = "车间简介"),
            edit = @Edit(title = "车间简介")
    )
    private String description;
    private Boolean deleted = false;
}
