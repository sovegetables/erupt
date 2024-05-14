package com.qamslink.mes.model.quality;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mes_inspection_method",uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@Erupt(name = "检验方法",
        orderBy = "MesInspectionMethod.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesInspectionMethod extends HyperModelVo {

    @EruptField(
            views = @View(title = "方法名称"),
            edit = @Edit(title = "方法名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述", notNull = true, type = EditType.TEXTAREA)
    )
    private String description;

    private Boolean deleted = false;
}
