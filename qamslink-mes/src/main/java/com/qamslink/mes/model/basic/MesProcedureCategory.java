package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.Tree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "mes_procedure_category")
@Erupt(name = "工序类型",
        tree = @Tree(pid = "id"),
        orderBy = "MesProcedureCategory.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesProcedureCategory extends HyperModel {

    @EruptField(
            views = @View(title = "类型名称"),
            edit = @Edit(title = "类型名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "类型编码"),
            edit = @Edit(title = "类型编码", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @EruptField(
            views = @View(
                    title = "描述"
            ),
            edit = @Edit(
                    title = "描述",
                    type = EditType.TEXTAREA
            )
    )
    private String description;

    private Boolean deleted = false;
}
