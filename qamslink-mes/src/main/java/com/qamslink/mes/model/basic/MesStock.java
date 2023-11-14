package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.helper.HyperModelVo;
import xyz.erupt.upms.model.EruptOrg;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "mes_stock", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Setter
@Getter
@Erupt(name = "物料列表",
//        dataProxy = MesStockService.class,
        orderBy = "MesStock.createTime desc",
        power = @Power(importable = true),
        linkTree = @LinkTree(field = "stockCategory", fieldClass = "MesStockCategory")
)
public class MesStock extends HyperModelVo {

    @EruptField(
            views = @View(title = "物料名称", highlight = true),
            edit = @Edit(title = "物料名称", search = @Search(vague = true), notNull = true)
    )
    private String name;

    @EruptField(
            views = @View(title = "物料编码"),
            edit = @Edit(title = "物料编码", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = @View(title = "物料分类", column = "name"),
            edit = @Edit(title = "物料分类", type = EditType.REFERENCE_TREE, notNull = true,
                    referenceTreeType = @ReferenceTreeType(pid = "parentCategory.id"))
    )
    private MesStockCategory stockCategory;

    @EruptField(
            views = @View(title = "规格型号"),
            edit = @Edit(title = "规格型号", search = @Search(vague = true))
    )
    private String spec;

    @ManyToOne
    @EruptField(
            views = @View(title = "生产部门", column = "name"),
            edit = @Edit(title = "生产部门", type = EditType.REFERENCE_TABLE)
    )
    private EruptOrg Org;

    @ManyToOne
    @EruptField(
            views = @View(title = "主计量单位", column = "name"),
            edit = @Edit(title = "主计量单位", notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private Unit unit;

    @ManyToOne
    @EruptField(
            views = @View(title = "销售默认单位" ,show = false,column = "name"),
            edit = @Edit(title = "销售默认单位", type = EditType.REFERENCE_TABLE)
    )
    private Unit salesUnitMeasure;
    @ManyToOne
    @EruptField(
            views = @View(title = "采购默认单位" ,show = false,column = "name"),
            edit = @Edit(title = "采购默认单位",type = EditType.REFERENCE_TABLE)

    )
    private Unit procurementUnitMeasure;
    @ManyToOne
    @EruptField(
            views = @View(title = "生产默认单位" ,show = false,column = "name"),
            edit = @Edit(title = "生产默认单位", type = EditType.REFERENCE_TABLE)

    )
    private Unit productionUnitMeasure;
    @ManyToOne
    @EruptField(
            views = @View(title = "库存默认单位" ,show = false,column = "name"),
            edit = @Edit(title = "库存默认单位", type = EditType.REFERENCE_TABLE)

    )
    private Unit inventoryUnitMeasure;

    @EruptField(
            views = @View(title = "版本"),
            edit = @Edit(title = "版本")
    )
    private String version;

    @EruptField(
            views = @View(title = "发料方式"),
            edit = @Edit(title = "发料方式", type = EditType.CHOICE, notNull = true,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "直接领料", value = "1"),
                            @VL(label = "直接倒冲", value = "2"),
                            @VL(label = "调拨领料", value = "3"),
                            @VL(label = "调拨倒冲", value = "4"),
                            @VL(label = "不发料", value = "5")
                    }))
    )
    private Integer issueType = 1;

    @EruptField(
            views = @View(title = "倒冲时机"),
            edit = @Edit(title = "倒冲时机", type = EditType.CHOICE, showBy = @ShowBy(dependField = "issueType", expr = "value == 3 || value == 4"),
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "入库倒冲", value = "1")
                    }))
    )
    private Integer backFlush;

    @EruptField(
            views = @View(title = "单位条码数"),
            edit = @Edit(title = "单位条码数", numberType = @NumberType(min = 0))
    )
    private BigDecimal capacity;

    @EruptField(
            views = @View(title = "检验类型"),
            edit = @Edit(title = "检验类型",
                    notNull = true,
                    type = EditType.CHOICE, choiceType = @ChoiceType(vl = {
                    @VL(label = "抽检", value = "1"),
                    @VL(label = "全检", value = "2")
            }))
    )
    private Integer inspectionType;

    @EruptField(
            views = @View(title = "是否来料检验"),
            edit = @Edit(title = "是否来料检验",
                    boolType = @BoolType(trueText = "是", falseText = "否"))
    )
    private Boolean iqcInspection = true;

    @EruptField(
            views = @View(title = "是否产品检验"),
            edit = @Edit(title = "是否产品检验",
                    boolType = @BoolType(trueText = "是", falseText = "否"))
    )
    private Boolean pqcInspection = true;

    @EruptField(
            views = @View(title = "是否出货检验"),
            edit = @Edit(title = "是否出货检验",
                    boolType = @BoolType(trueText = "是", falseText = "否"))
    )
    private Boolean fqcInspection = true;

    private String label;
}
