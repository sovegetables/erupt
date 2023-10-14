package com.qamslink.mes.model.quality;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mes_inspection_item")
@Getter
@Setter
@Erupt(name = "检验项目",
//        dataProxy = MesInspectionItemService.class,
        orderBy = "MesInspectionItem.createTime desc",
        filter = @Filter(value = "MesInspectionItem.tenantId",
                params = {"and MesInspectionItem.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_inspection_item set deleted = true where id = ?")
public class MesInspectionItem extends TenantCreatorModel {

    @EruptField(
            views = @View(title = "检测项名称"),
            edit = @Edit(title = "检测项名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", type = EditType.CHOICE,
                    show = true,
                    notNull = true,
                    search = @Search(vague = false),
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "IQC",value = "1"),
                            @VL(label = "IPQC", value = "2"),
                            @VL(label = "OQC", value = "3")
            }))
    )
    private Integer type;

    @EruptField(
            views = @View(title = "检测方法"),
            edit = @Edit(title = "检测方法", type = EditType.TEXTAREA)
    )
    private String inspectionMethod;

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

    @EruptField
    private Long tenantId;

    private Boolean deleted = false;
}
