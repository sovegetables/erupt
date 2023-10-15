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
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_inspection_item_measure")
@Getter
@Setter
@Erupt(name = "检测项",
//        dataProxy = MesInspectionItemMeasureService.class,
        filter = @Filter(value = "MesInspectionItemMeasure.tenantId",
                params = {"and MesInspectionItemMeasure.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_inspection_item_measure set deleted = true where id = ?")
public class MesInspectionItemMeasure extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views = {@View(title = "检测项名称", column = "name"),
                    @View(title = "检测方法", column = "inspectionMethod"),
                    @View(title = "描述", column = "description")},
            edit = @Edit(title = "检测项名称", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesInspectionItem inspectionItem;

    @EruptField(
            views = @View(title = "检测标准"),
            edit = @Edit(title = "检测标准", notNull = true, type = EditType.TEXTAREA)
    )
    private String measure;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", notNull = true, search = @Search(vague = true),
                    boolType = @BoolType(trueText = "启动", falseText = "禁用")
            )
    )
    private Boolean status = true;

    @ManyToOne
    @EruptField(
            views = {@View(title = "检验模板", column = "name")},
            edit = @Edit(title = "检验模板", search = @Search(vague = true), type = EditType.REFERENCE_TABLE, show = false)
    )
    private MesInspectionMould inspectionMould;

    private Boolean deleted = false;
}
