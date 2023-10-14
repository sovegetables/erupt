package com.qamslink.mes.model.warehouse;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "mes_warehouse_category")
@Erupt(name = "仓库类型",
//        dataProxy = MesWarehouseCategoryService.class,
        orderBy = "MesWarehouseCategory.createTime desc",
//        power = @Power(add = false, delete = false),
        filter = @Filter(value = "MesWarehouseCategory.tenantId",
                params = {"and MesWarehouseCategory.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_warehouse_category set deleted = true where id = ?")
public class MesWarehouseCategory extends HyperModel {
    @EruptField(
            views = @View(title = "仓库类型名称"),
            edit = @Edit(title = "仓库类型名称", search = @Search(vague = true), notNull = true)
    )
    private String name;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @EruptField(
            views = @View(title = "是否是虚拟仓"),
            edit = @Edit(title = "是否是虚拟仓", type = EditType.BOOLEAN,boolType = @BoolType(trueText = "是", falseText = "否")
    ))
    private Boolean virtualStorage = false;

    @EruptField
    private Long tenantId;

    private Boolean deleted = false;
}
