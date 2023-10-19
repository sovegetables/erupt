package com.qamslink.mes.model.maintenance;

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
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "mes_maintenance")
@Getter
@Setter
@Erupt(name = "保养模板",
//        dataProxy = MesMaintenanceService.class,
        orderBy = "MesMaintenance.createTime desc",
        filter = @Filter(value = "MesMaintenance.tenantId",
                params = {"and MesMaintenance.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_maintenance set deleted = true where id = ?")
public class MesMaintenance extends HyperModelVo {

    @EruptField(
            views = @View(title = "保养模板名称"),
            edit = @Edit(title = "保养模板名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "版本号"),
            edit = @Edit(title = "版本号", notNull = true)
    )
    private String version;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态",notNull = true, search = @Search(vague = true),
                    boolType = @BoolType(trueText = "启动", falseText = "禁用")
            )
    )
    private Boolean status = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mes_maintenance_detail",
            joinColumns = @JoinColumn(name = "maintenance_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "maintenance_item_id", referencedColumnName = "id"))
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(
                    title = "详情",
                    type = EditType.TAB_TABLE_REFER
            )
    )
    private Set<MesMaintenanceItem> maintenanceItem;

    @EruptField(
            views = @View(title = "租户", show = false, columnShowed = false)
    )


    private Boolean deleted = false;
}
