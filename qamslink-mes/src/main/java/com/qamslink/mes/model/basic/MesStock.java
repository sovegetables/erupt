package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.TenantModel;
import xyz.erupt.upms.model.EruptOrg;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "mes_stock")
@Setter
@Getter
@Erupt(name = "物料列表",
//        dataProxy = MesStockService.class,
        orderBy = "MesStock.createTime desc",
        power = @Power(importable = true),
        linkTree = @LinkTree(field = "stockCategory", fieldClass = "MesStockCategory"),
        filter = @Filter(value = "MesStock.tenantId", params = {"and MesStock.deleted = false"}, conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_stock set deleted = true where id = ?")
public class MesStock extends TenantModel {

    @EruptField(
            views = @View(title = "物料名称"),
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

    @EruptField(
            views = @View(title = "主计量单位"),
            edit = @Edit(title = "主计量单位", notNull = true)
    )
    private String unit;

    @ManyToOne
    @EruptField(
            views = @View(title = "销售默认单位" ,show = false,column = "name"),
            edit = @Edit(title = "销售默认单位", type = EditType.REFERENCE_TABLE)

    )
    private MesUnitMeasureCode salesUnitMeasure;
    @ManyToOne
    @EruptField(
            views = @View(title = "采购默认单位" ,show = false,column = "name"),
            edit = @Edit(title = "采购默认单位",type = EditType.REFERENCE_TABLE)

    )
    private MesUnitMeasureCode procurementUnitMeasure;
    @ManyToOne
    @EruptField(
            views = @View(title = "生产默认单位" ,show = false,column = "name"),
            edit = @Edit(title = "生产默认单位", type = EditType.REFERENCE_TABLE)

    )
    private MesUnitMeasureCode productionUnitMeasure;
    @ManyToOne
    @EruptField(
            views = @View(title = "库存默认单位" ,show = false,column = "name"),
            edit = @Edit(title = "库存默认单位", type = EditType.REFERENCE_TABLE)

    )
    private MesUnitMeasureCode inventoryUnitMeasure;

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
            views = @View(title = "是否需要来料检验"),
            edit = @Edit(title = "是否需要来料检验",
                    boolType = @BoolType(trueText = "是", falseText = "否"))
    )
    private Boolean iqcInspection = true;

    @EruptField(
            views = @View(title = "是否需要产品检验"),
            edit = @Edit(title = "是否需要产品检验",
                    boolType = @BoolType(trueText = "是", falseText = "否"))
    )
    private Boolean pqcInspection = true;

    @EruptField(
            views = @View(title = "是否需要出货检验"),
            edit = @Edit(title = "是否需要出货检验",
                    boolType = @BoolType(trueText = "是", falseText = "否"))
    )
    private Boolean fqcInspection = true;

//    @Transient
//    @OneToMany
//    @EruptField(
//            views = @View(title = "工序列表", show = false),
//            edit = @Edit(
//                    title = "工序列表",
//                    type = EditType.TAB_TABLE_REFER,
//                    show = false,
//                    readonly = @Readonly
//            ),
//            extra = @Extra(clazz = MesBomWorkingProcedureVO.class, idClass = BaseModel.class, params = {"id"},
//                    sql = "select wp.id, wp.name, bwp.sort from mes_bom_working_procedure bwp left join mes_bom b on b.id = bwp.bom_id " +
//                            "left join mes_working_procedure wp on wp.id = bwp.working_procedure_id where b.main_stock_id = #{0}")
//    )
//    private List<MesBomWorkingProcedureVO> bomWorkingProcedures;

    private String label;
    private Boolean deleted = false;
}
