package com.qamslink.mes.model.basic;

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
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "mes_packing_list")
@Erupt(name = "包装箱列表",
        orderBy = "MesPackingList.createTime desc",
        linkTree = @LinkTree(field = "packinglistCategory", fieldClass = "MesPackingListCategory"),
        power = @Power(importable = true),
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesPackingList extends HyperModelVo {

    @EruptField(
            views = @View(title = "名称"),
            edit = @Edit(title = "名称", search = @Search(vague = true), notNull = true)
    )
    private String name;

    @ManyToOne
    @EruptField(
            views = @View(title = "类型" ,column = "name"),
            edit = @Edit(title = "类型", type = EditType.REFERENCE_TABLE, notNull = true, search = @Search(vague = true))

    )
    private MesPackingListCategory packinglistCategory;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", type = EditType.CHOICE, notNull = true,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "Enable", value = "0"),
                            @VL(label = "Disable", value = "1")
                    }))
    )
    private Integer statusType = 1;

    @EruptField(
            views = @View(title = "高（mm）", show = false),
            edit = @Edit(title = "高（mm）",type = EditType.NUMBER)
    )
    private Double packingHeight;

    @EruptField(
            views = @View(title = "宽（mm）", show = false),
            edit = @Edit(title = "宽（mm））",type = EditType.NUMBER)
    )
    private Double packingWidth;

    @EruptField(
            views = @View(title = "长（mm）", show = false),
            edit = @Edit(title = "长（mm）",type = EditType.NUMBER)
    )
    private Double packingDepth;

    @EruptField(
            views = @View(title = "最大重量（kg）", show = false),
            edit = @Edit(title = "最大重量（kg）",type = EditType.NUMBER)
    )
    private Double maxWeight;

    @EruptField(
            views = @View(title = "包装箱自重（kg）", show = false),
            edit = @Edit(title = "包装箱自重（kg）",type = EditType.NUMBER)
    )
    private Double packingWeight;

    @EruptField(
            views = @View(title = "工单混合包装"),
            edit = @Edit(title = "工单混合包装", type = EditType.CHOICE, notNull = true,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "不是", value = "0"),
                            @VL(label = "是", value = "1")
                    }))
    )
    private Integer orderMixType = 1;

    @EruptField(
            views = @View(title = "产品混合包装"),
            edit = @Edit(title = "产品混合包装", type = EditType.CHOICE, notNull = true,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "不是", value = "0"),
                            @VL(label = "是", value = "1")
                    }))
    )
    private Integer productMixType = 1;

    @EruptField(
            views = @View(title = "是否顺序包装"),
            edit = @Edit(title = "是否顺序包装", type = EditType.CHOICE, notNull = true,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "不是", value = "0"),
                            @VL(label = "是", value = "1")
                    }))
    )
    private Integer sequenceType = 1;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述",type = EditType.TEXTAREA)
    )
    private String description;
    private Boolean deleted = false;
}
