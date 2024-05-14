package com.qamslink.mes.model.warehouse;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.Tree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;
import xyz.erupt.upms.model.EruptOrg;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "mes_warehouse")
@Erupt(name = "仓库列表",
        orderBy = "MesWarehouse.createTime desc",
        power = @Power(importable = true),
        tree = @Tree(pid = "id"),
        linkTree = @LinkTree(field = "warehouseCategory", fieldClass = "MesWarehouseCategory"),
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesWarehouse extends HyperModelVo {

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
            edit = @Edit(title = "仓库类型",type = EditType.REFERENCE_TABLE)
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
    private Boolean deleted = false;
}
