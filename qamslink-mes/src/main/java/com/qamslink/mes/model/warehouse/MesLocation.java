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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "mes_location")
@Erupt(name = "库位管理",linkTree = @LinkTree(field = "warehouse"),
        orderBy = "MesLocation.createTime desc",
//        dataProxy = MesLocationService.class,
        filter = @Filter(value = "MesLocation.tenantId",
                params = {"and MesLocation.deleted = false"},
                conditionHandler = TenantFilter.class))

@SQLDelete(sql = "update mes_location set deleted = true where id = ?")
public class MesLocation extends TenantModel {
    @EruptField(
            views = @View(title = "库位条码"),
            edit = @Edit(title = "库位条码", search = @Search(vague = true), notNull = true)
    )
    private String locationCode;
    @ManyToOne
    @EruptField(
            views = {@View(title = "所属仓库", column = "name"), @View(title = "仓库编码", column = "code", show = false)},
            edit = @Edit(title = "所属仓库", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesWarehouse warehouse;
    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述")
    )
    private String description;
    private Boolean deleted = false;
}
