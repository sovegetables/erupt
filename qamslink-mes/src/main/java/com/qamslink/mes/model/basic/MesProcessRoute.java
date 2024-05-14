package com.qamslink.mes.model.basic;


import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mes_process_route", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Setter
@Getter
@Erupt(name = "工艺路线",
        orderBy = "MesProcessRoute.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesProcessRoute extends HyperModelVo {

    @EruptField(
            views = @View(title = "编码", highlight = true),
            edit = @Edit(title = "编码", notNull = true)
    )
    private String code;

    @EruptField(
            views = @View(title = "名称"),
            edit = @Edit(title = "名称", notNull = true)
    )
    private String name;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述")
    )
    private String description;

    @EruptField(
            edit = @Edit(title = "明细", notNull = true, type = EditType.CODE_EDITOR)
    )
    private String detailJson;

    private Boolean deleted = false;
}
