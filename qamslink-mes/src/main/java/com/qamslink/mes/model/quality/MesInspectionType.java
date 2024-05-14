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
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mes_inspection_type",uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Getter
@Setter
@Erupt(name = "检验类型",
        orderBy = "MesInspectionType.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesInspectionType extends HyperModelVo {

    @EruptField(
            views = @View(title = "类型编码", highlight = true),
            edit = @Edit(title = "类型编码", placeHolder = "保存时自动生成", search = @Search(vague = true))
    )
    @CodeGenerator
    private String code;

    @EruptField(
            views = @View(title = "类型名称"),
            edit = @Edit(title = "类型名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "QC类型"),
            edit = @Edit(title = "QC类型", type = EditType.CHOICE,
                    notNull = true,
                    search = @Search(),
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "IQC",value = "IQC"),
                            @VL(label = "IPQC", value = "IPQC"),
                            @VL(label = "PQC", value = "PQC"),
                            @VL(label = "FQC", value = "FQC"),
                            @VL(label = "OQC", value = "OQC"),
                            @VL(label = "FAI", value = "FAI"),
            }))
    )
    private String type;

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
