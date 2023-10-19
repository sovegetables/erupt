package com.qamslink.mes.model.basic;

import com.qamslink.mes.converter.LabelRuleTypeConverter;
import com.qamslink.mes.converter.LabelTypeConverter;
import com.qamslink.mes.model.equipment.MesEquipmentCategory;
import com.qamslink.mes.type.LabelRuleType;
import com.qamslink.mes.type.LabelType;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;

@Entity
@Table(name = "mes_stock_barcode", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Getter
@Setter
@Erupt(name = "标签配置",
//        dataProxy = MesStockBarcodeService.class,
        orderBy = "MesStockBarcode.createTime desc"
)
public class MesStockBarcode extends HyperModelVo {

    @EruptField(
            views = @View(
                    title = "编码", highlight = true
            ),
            edit = @Edit(
                    title = "编码",
                    type = EditType.INPUT,
                    notNull = true,
                    placeHolder = "保存时自动生成"
            )
    )
    @CodeGenerator
    private String code;

    @EruptField(
            views = @View(title = "标签类型"),
            edit = @Edit(title = "标签类型", notNull = true, type = EditType.CHOICE, search = @Search(vague = false),
                    choiceType = @ChoiceType(fetchHandler = LabelType.Handler.class))
    )
    @Convert(converter = LabelTypeConverter.class)
    private LabelType categoryType;

    @EruptField(
            views = @View(title = "匹配规则"),
            edit = @Edit(
                    title = "匹配规则",
                    notNull = true,
                    type = EditType.CHOICE,
                    search = @Search(vague = false),
                    showBy = @ShowBy(dependField = "categoryType", expr = "value != null"),
                    choiceType = @ChoiceType(fetchHandler = LabelRuleType.Handler.class)
            )
    )
    @Convert(converter = LabelRuleTypeConverter.class)
    private LabelRuleType type;

    @ManyToOne
    @EruptField(
            views = @View(title = "标签模板", column = "name"),
            edit = @Edit(title = "标签模板", notNull = true,  search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesBarcodeModel barcodeModel;

    @ManyToOne
    @EruptField(
            views = @View(title = "物料编码", column = "code"),
            edit = @Edit(title = "物料编码",  search = @Search(vague = true, value = false), type = EditType.REFERENCE_TABLE,
                    showBy = @ShowBy(dependField = "type", expr = "value == " + LabelRuleType.TYPE_MATERIAL_CODE))
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = @View(title = "物料分类", column = "name"),
            edit = @Edit(title = "物料分类", search = @Search(vague = true, value = false), type = EditType.REFERENCE_TABLE,
                    showBy = @ShowBy(dependField = "type", expr = "value == " + LabelRuleType.TYPE_MATERIAL_CATEGORY))
    )
    private MesStockCategory stockCategory;

    @ManyToOne
    @EruptField(
            views = @View(title = "设备类型", column = "name", show = false),
            edit = @Edit(title = "设备类型",
                    search = @Search(vague = true, value = false),
                    type = EditType.REFERENCE_TABLE, show = false,
                    showBy = @ShowBy(dependField = "type", expr = "value == 8"))
    )
    private MesEquipmentCategory equipmentCategory;
}
