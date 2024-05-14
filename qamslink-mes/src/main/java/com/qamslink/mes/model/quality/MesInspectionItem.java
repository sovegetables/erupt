package com.qamslink.mes.model.quality;

import com.qamslink.mes.model.basic.Unit;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_inspection_item")
@Getter
@Setter
@Erupt(name = "检验项",
        linkTree = @LinkTree(field = "category", fieldClass = "MesInspectionItemCategory"),
        orderBy = "MesInspectionItem.sort desc",
        power = @Power(importable = true),
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesInspectionItem extends HyperModelVo {

    @EruptField(
            views = @View(title = "名称"),
            edit = @Edit(title = "名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @ManyToOne
    @EruptField(
            views = @View(title = "所属分类",column = "name"),
            edit = @Edit(
                    title = "所属分类",
                    type = EditType.REFERENCE_TREE,
                    referenceTreeType = @ReferenceTreeType(pid = "parentCategory.id", expandLevel = 3)
            )
    )
    private MesInspectionItemCategory category;

    @EruptField(
            views = @View(title = "录入方式"),
            edit = @Edit(title = "录入方式", type = EditType.CHOICE,
                    show = true,
                    notNull = true,
                    search = @Search(),
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "指定值",value = "1"),
                            @VL(label = "固定结果", value = "2"),
            }))
    )
    private Integer type;

    @ManyToOne
    @EruptField(
            views = @View(title = "单位",column = "name"),
            edit = @Edit(
                    title = "单位",
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType()
            )
    )
    private Unit unit;

    @ManyToOne
    @EruptField(
            views = @View(title = "检测方法", column = "name"),
            edit = @Edit(
                    title = "检测方法",
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType()
            )
    )
    private MesInspectionMethod inspectionMethod;

    @EruptField(
            edit = @Edit(title = "排序", type = EditType.NUMBER)
    )
    private Integer sort = 0;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述", type = EditType.TEXTAREA)
    )
    private String description;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态",notNull = true, search = @Search(vague = true),
                boolType = @BoolType(trueText = "启动", falseText = "禁用")
            )
    )
    private Boolean status = true;

    private Boolean deleted = false;
}
