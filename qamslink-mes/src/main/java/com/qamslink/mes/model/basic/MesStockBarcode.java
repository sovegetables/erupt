package com.qamslink.mes.model.basic;

import com.qamslink.mes.model.equipment.MesEquipmentCategory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
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
@Table(name = "mes_stock_barcode")
@Getter
@Setter
@Erupt(name = "标签配置",
//        dataProxy = MesStockBarcodeService.class,
        orderBy = "MesStockBarcode.createTime desc",
        filter = @Filter(value = "MesStockBarcode.tenantId",
                params = {"and MesStockBarcode.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_stock_barcode set deleted = true where id = ?")
public class MesStockBarcode extends HyperModelVo {

    public static final int TYPE_MATERIAL_CODE = 1;
    public static final int TYPE_MATERIAL_CATEGORY = 2;
    public static final int TYPE_MATERIAL_ALL = 3;

    public static final int CATEGORY_MATERIAL = 1;
    public static final int CATEGORY_PRODUCT_MATERIAL = 2;
    public static final int CATEGORY_HISTORY_MATERIAL = 3;
    public static final int CATEGORY_RETURN_MATERIAL = 4;
    public static final int CATEGORY_FLOW_CARD = 5;
    public static final int CATEGORY_SCRAP_MATERIAL = 6;

    @EruptField(
            views = @View(title = "标签类型"),
            edit = @Edit(title = "标签类型", notNull = true, type = EditType.CHOICE,search = @Search(vague = false),
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "物料条码", value = CATEGORY_MATERIAL + ""),
                            @VL(label = "产品条码", value = CATEGORY_PRODUCT_MATERIAL + ""),
                            @VL(label = "历史条码", value = CATEGORY_HISTORY_MATERIAL + ""),
                            @VL(label = "退货条码", value = CATEGORY_RETURN_MATERIAL + ""),
                            @VL(label = "废料条码", value = CATEGORY_SCRAP_MATERIAL + ""),
                            @VL(label = "流转卡", value = CATEGORY_FLOW_CARD + ""),
                    }))
    )
    private Integer categoryType;

    @EruptField(
            views = @View(title = "匹配规则"),
            edit = @Edit(
                    title = "匹配规则",
                    notNull = true,
                    type = EditType.CHOICE,
                    search = @Search(vague = false),
                    showBy = @ShowBy(dependField = "type", expr = "value <= " + TYPE_MATERIAL_CODE),
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "物料条码", value = TYPE_MATERIAL_CODE + ""),
                            @VL(label = "物料分类", value = TYPE_MATERIAL_CATEGORY + ""),
                            @VL(label = "所有物料", value = TYPE_MATERIAL_ALL + ""),
                    })
            )
    )
    private Integer type = TYPE_MATERIAL_ALL;

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
                    showBy = @ShowBy(dependField = "type", expr = "value == " + TYPE_MATERIAL_CODE))
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = @View(title = "物料分类", column = "name"),
            edit = @Edit(title = "物料分类", search = @Search(vague = true, value = false), type = EditType.REFERENCE_TABLE,
                    showBy = @ShowBy(dependField = "type", expr = "value == " + TYPE_MATERIAL_CATEGORY))
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

    private Boolean deleted = false;
}
