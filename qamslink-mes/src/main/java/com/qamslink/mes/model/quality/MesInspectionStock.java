package com.qamslink.mes.model.quality;

import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.basic.MesStockCategory;
import com.qamslink.mes.model.basic.MesWorkingProcedure;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.ShowBy;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_inspection_stock")
@Getter
@Setter
@Erupt(name = "物料检验模板",
//        dataProxy = MesInspectionStockService.class,
        orderBy = "MesInspectionStock.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(value = "MesInspectionStock.tenantId",
                params = {"and MesInspectionStock.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_inspection_stock set deleted = true where id = ?")
public class MesInspectionStock extends HyperModelVo {

    public static final int TYPE_MATERIAL_CODE = 1;
    public static final int TYPE_MATERIAL_CATEGORY = 2;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", notNull = true, search = @Search(vague = true, value = false), type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "物料编码", value = TYPE_MATERIAL_CODE + ""),
                            @VL(label = "物料分类", value = TYPE_MATERIAL_CATEGORY + "")
                    }
            ))
    )
    private Integer type = TYPE_MATERIAL_CODE;

    @ManyToOne
    @EruptField(
            views = {@View(title = "物料名称", column = "name"), @View(title = "物料编码", column = "code")},
            edit = @Edit(title = "物料名称", search = @Search(vague = true, value = false), type = EditType.REFERENCE_TABLE
                    , showBy = @ShowBy(dependField = "type", expr = "value == 1")
            )
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = @View(title = "物料分类", column = "name"),
            edit = @Edit(title = "物料分类", search = @Search(vague = true, value = false), type = EditType.REFERENCE_TABLE
                    , showBy = @ShowBy(dependField = "type", expr = "value == 2")
            )
    )
    private MesStockCategory stockCategory;

    @ManyToOne
    @EruptField(
            views = @View(title = "检验模板", column = "name"),
            edit = @Edit(title = "检验模板", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesInspectionMould inspectionMould;

    @EruptField(
            views = @View(title = "品质检验类型"),
            edit = @Edit(title = "品质检验类型", type = EditType.CHOICE, notNull = true, search = @Search,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "IQC", value = "1"),
                            @VL(label = "PQC", value = "2"),
                            @VL(label = "FQC", value = "3")
                    }))
    )
    private Integer qualityInspectionType;

    @EruptField(
            views = @View(title = "检验类型"),
            edit = @Edit(title = "检验类型", notNull = true, search = @Search, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "抽检", value = "1"),
                            @VL(label = "全检", value = "2")
                    }
            ))
    )
    private Integer inspectionType;

    @ManyToOne
    @EruptField(
            views = @View(title = "工序", column = "name"),
            edit = @Edit(title = "工序", search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesWorkingProcedure mesWorkingProcedure;

    @EruptField(
            views = @View(title = "租户", show = false, columnShowed = false)
    )


    private Boolean deleted = false;
}
