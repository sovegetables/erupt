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
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mes_aql_rule")
@Getter
@Setter
@Erupt(name = "AQL抽样规则",
        orderBy = "MesAQLRule.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesAQLRule extends HyperModelVo {

    @EruptField(
            views = @View(title = "规则名"),
            edit = @Edit(title = "规则名", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "规则类别"),
            edit = @Edit(title = "规则类别", type = EditType.CHOICE,
                    notNull = true,
                    search = @Search(),
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "正常",value = "1"),
                            @VL(label = "加严", value = "2"),
                            @VL(label = "放宽", value = "3"),
            }))
    )
    private Integer type = 1;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述", type = EditType.TEXTAREA)
    )
    private String description;

    private Boolean deleted = false;
}
