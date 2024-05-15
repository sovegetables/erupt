package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.Tree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mes_packinglist_category")
@Setter
@Getter
@Erupt(name = "包装箱类型",
        tree = @Tree(pid = "id"),
        power = @Power(importable = true),
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesPackingListCategory extends HyperModelVo {
    @EruptField(
            views = @View(title = "类别"),
            edit = @Edit(title = "类别", search = @Search(vague = true), notNull = true)
    )
    private String name;

    @EruptField(
            views = @View(title = "数据类型"),
            edit = @Edit(title = "数据类型")
    )
    private String dataType;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述")
    )
    private String description;

    private Boolean deleted = false;
}
