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
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mes_maintenance_item")
@Getter
@Setter
@Erupt(name = "保养项",
//        dataProxy = MesMaintenanceItemService.class,
        orderBy = "MesMaintenanceItem.createTime desc",
        filter = @Filter(value = "MesMaintenanceItem.tenantId",
                params = {"and MesMaintenanceItem.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_maintenance_item set deleted = true where id = ?")
public class MesMaintenanceItem extends TenantCreatorModel {

    @EruptField(
            views = @View(title = "保养项名称"),
            edit = @Edit(title = "保养项名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", type = EditType.CHOICE,notNull = true, search = @Search,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "设备",value = "1"),
                            @VL(label = "工具", value = "2")
            }))
    )
    private Integer type;

    @EruptField(
            views = @View(title = "保养方法"),
            edit = @Edit(title = "保养方法", type = EditType.TEXTAREA)
    )
    private String maintenanceMethod;

    @EruptField(
            views = @View(title = "保养时长（小时）", sortable = true),
            edit = @Edit(title = "保养时长（小时）")
    )
    private Float duration;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述", type = EditType.TEXTAREA)
    )
    private String description;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态",notNull = true, search = @Search(vague = true),
                boolType = @BoolType(trueText = "启动", falseText = "禁用")
            )
    )
    private Boolean status = true;

    @EruptField
    private Long tenantId;

    private Boolean deleted = false;
}
