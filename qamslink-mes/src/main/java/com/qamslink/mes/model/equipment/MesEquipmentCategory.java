package com.qamslink.mes.model.equipment;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "mes_equipment_category")
@Erupt(name = "设备分类",
//        dataProxy = MesEquipmentCategoryService.class,
        filter = @Filter(value = "MesEquipmentCategory.tenantId",
                conditionHandler = TenantFilter.class))
public class MesEquipmentCategory extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "设备分类名称"),
            edit = @Edit(title = "设备分类名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "设备分类编码"),
            edit = @Edit(title = "设备分类编码", notNull = true, search = @Search(vague = true))
    )
    private String code;
    private Boolean deleted = false;
}
