package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mes_unit_measure_code", uniqueConstraints = @UniqueConstraint(columnNames = "unitCode"))
@Setter
@Getter
@Erupt(name = "计量单位",
        power = @Power(importable = true),
        orderBy = "Unit.createTime desc"
)
public class Unit extends HyperModelVo {
    @EruptField(
            views = @View(title = "编码", highlight = true),
            edit = @Edit(title = "编码", placeHolder = "保存时自动生成", search = @Search(vague = true), notNull = true)
    )
    @CodeGenerator
    private String unitCode;

    @EruptField(
            views = @View(title = "名称"),
            edit = @Edit(title = "名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述", search = @Search(vague = true))
    )
    private String description;
}
