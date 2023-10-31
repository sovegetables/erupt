package com.qamslink.mes.model.tool;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "mes_mould_component")
@Setter
@Getter
@Erupt(name = "构件列表",
//        dataProxy = MesMouldComponentService.class,
        orderBy = "MesMouldComponent.createTime desc",
        linkTree = @LinkTree(field = "toolCategory" , fieldClass = "MesToolCategory"),
        power = @Power(importable = true)
)
public class MesMouldComponent extends HyperModelVo {

    @EruptField(
            views = @View(title = "构件编码", highlight = true),
            edit = @Edit(title = "构件编码", placeHolder = "保存时自动生成", search = @Search(vague = true))
    )
    @CodeGenerator
    private String code;

    @EruptField(
            views = @View(title = "构件名称"),
            edit = @Edit(title = "构件名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @ManyToOne
    @EruptField(
            views = @View(title = "所属分类", column = "name"),
            edit = @Edit(title = "所属分类", type = EditType.REFERENCE_TABLE, notNull = true)
    )
    private MesToolCategory toolCategory;

    @EruptField(
            views = @View(title = "安全库存"),
            edit = @Edit(title = "安全库存", numberType = @NumberType(min = 0))
    )
    private BigDecimal safeCapacity;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;
}
