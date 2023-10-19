package com.qamslink.mes.model.basic;

import com.qamslink.mes.model.equipment.MesEquipment;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "mes_resource")
@Erupt(name = "工位定义",
//        dataProxy = MesResourceService.class,
        orderBy = "MesResource.createTime desc",
        power = @Power(importable = true)
        )
public class MesResource extends HyperModelVo {

    @EruptField(
            views = @View(title = "编码", highlight = true),
            edit = @Edit(title = "编码", placeHolder = "保存时自动生成",
                    search = @Search(vague = true), notNull = true)
    )
    @CodeGenerator
    private String code;

    @EruptField(
            views = @View(title = "名称"),
            edit = @Edit(title = "名称", search = @Search(vague = true), notNull = true)
    )
    private String name;

    @ManyToOne
    @EruptField(
            views = {@View(title = "产线", column = "name")},
            edit = @Edit(
                    title = "产线",
                    search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE,
                    notNull = true
            )
    )
    private MesProductLine productLine;

    @ManyToOne
    @EruptField(
            views = {@View(title = "工序", column = "name")},
            edit = @Edit(
                    title = "工序",
                    search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE
            )
    )
    private MesWorkingProcedure workingProcedure;

    @ManyToOne
    @EruptField(
            views = @View(title = "设备", column = "name"),
            edit = @Edit(title = "设备",type = EditType.REFERENCE_TABLE)
    )
    private MesEquipment equipment;

    @EruptField(
            views = @View(title = "状态", sortable = true),
            edit = @Edit(
                    title = "状态",
                    search = @Search,
                    type = EditType.BOOLEAN,
                    notNull = true,
                    boolType = @BoolType(
                            trueText = "可用",
                            falseText = "不可用"
                    )
            )
    )
    private Boolean status = true;
    private Boolean deleted = false;
}
