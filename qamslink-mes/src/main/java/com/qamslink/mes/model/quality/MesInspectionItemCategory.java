package com.qamslink.mes.model.quality;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.Tree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTreeType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mes_inspection_item_category",uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@Erupt(name = "检验项分类",
        linkTree = @LinkTree(field = "parentCategory"),
        tree = @Tree(pid = "id", expandLevel = 3),
        orderBy = "MesInspectionItemCategory.sort asc",
        power = @Power(importable = true),
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesInspectionItemCategory extends HyperModelVo {

    @EruptField(
            views = @View(title = "分类名称"),
            edit = @Edit(title = "分类名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @ManyToOne
    @EruptField(
            views = @View(title = "上级分类",column = "name"),
            edit = @Edit(
                    title = "上级分类",
                    type = EditType.REFERENCE_TREE,
                    referenceTreeType = @ReferenceTreeType(pid = "parentCategory.id", expandLevel = 3)
            )
    )
    private MesInspectionItemCategory parentCategory;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述", type = EditType.TEXTAREA)
    )
    private String description;

    @EruptField(
            edit = @Edit(title = "排序", type = EditType.NUMBER)
    )
    private Integer sort = 0;

    private Boolean deleted = false;
}
