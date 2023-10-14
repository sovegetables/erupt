package com.qamslink.mes.model.warehouse;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.TenantModel;
import xyz.erupt.upms.model.EruptOrg;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "mes_warehouse")
@Erupt(name = "仓库列表",
//        dataProxy = MesWarehouseService.class,
        orderBy = "MesWarehouse.createTime desc",
        linkTree = @LinkTree(field = "warehouseCategory", fieldClass = "MesWarehouseCategory"),
        filter = @Filter(value = "MesWarehouse.tenantId",
                params = {"and MesWarehouse.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_warehouse set deleted = true where id = ?")
public class MesWarehouse extends TenantModel {

    @EruptField(
            views = @View(title = "仓库名称"),
            edit = @Edit(title = "仓库名称", search = @Search(vague = true), notNull = true)
    )
    private String name;

    @EruptField(
            views = @View(title = "仓库编码"),
            edit = @Edit(title = "仓库编码", search = @Search(vague = true), notNull = true)
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = @View(title = "仓库类型", column = "name"),
            edit = @Edit(title = "仓库类型", notNull = true,type = EditType.REFERENCE_TABLE)
    )
    private MesWarehouseCategory warehouseCategory;

    @ManyToOne
    @EruptField(
            views = @View(title = "关联部门", column = "name"),
            edit = @Edit(title = "关联部门",type = EditType.REFERENCE_TABLE)
    )
    private EruptOrg Org;

    @EruptField(
            views = @View(title = "仓库地址"),
            edit = @Edit(title = "仓库地址")
    )
    private String address;

    private Long tenantId;

    private Boolean deleted = false;
}
