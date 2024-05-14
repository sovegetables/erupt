package com.qamslink.mes.model.basic;

import com.qamslink.mes.model.warehouse.MesWarehouse;
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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "mes_stock", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Setter
@Getter
@Erupt(name = "物料列表",
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
            edit = @Edit(title = "物料分类", type = EditType.REFERENCE_TREE,
                    referenceTreeType = @ReferenceTreeType(pid = "parentCategory.id"))
    )
    private MesStockCategory stockCategory;

    @EruptField(
            views = @View(title = "规格型号"),
            edit = @Edit(title = "规格型号", search = @Search(vague = true))
    )
    private String spec;

    @EruptField(
            views = @View(title = "版本"),
            edit = @Edit(title = "版本", search = @Search(vague = true))
    )
    private String version;

    @ManyToOne
    @EruptField(
            views = {@View(title = "单位", column = "name"),  @View(title = "id", column = "id", show = false)},
            edit = @Edit(title = "单位", type = EditType.REFERENCE_TABLE)
    )
    private Unit unit;

    @ManyToOne
    @EruptField(
            views = @View(title = "工艺路线", column = "name"),
            edit = @Edit(title = "工艺路线", type = EditType.REFERENCE_TABLE)
    )
    private MesProcessRoute route;

    @ManyToOne
    @EruptField(
            views = @View(title = "默认仓库", column = "name"),
            edit = @Edit(title = "默认仓库", type = EditType.REFERENCE_TABLE)
    )
    private MesWarehouse warehouse;

    @ManyToOne
    @EruptField(
            views = @View(title = "生产部门", column = "name"),
            edit = @Edit(title = "生产部门", type = EditType.REFERENCE_TABLE)
    )
    private EruptOrg Org;

    @EruptField(
            views = @View(title = "检验方式"),
            edit = @Edit(title = "检验方式",
                    notNull = true,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                        @VL(label = "全检", value = "1"),
                        @VL(label = "免检", value = "2")
                    })
            )
    )
    private Integer inspectionType = 1;

    @EruptField(
            views = @View(title = "来料检验"),
            edit = @Edit(title = "来料检验",
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "不需要", value = "false"),
                            @VL(label = "需要", value = "true")
                    })
            )
    )
    private Boolean iqcInspection = false;

    @EruptField(
            views = @View(title = "生产检验"),
            edit = @Edit(title = "生产检验",
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "不需要", value = "false"),
                            @VL(label = "需要", value = "true")
                    })
            )
    )
    private Boolean pqcInspection = false;

    @EruptField(
            views = @View(title = "出货检验"),
            edit = @Edit(title = "出货检验",
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "不需要", value = "false"),
                            @VL(label = "需要", value = "true")
                    })
            )
    )
    private Boolean fqcInspection = false;
}
