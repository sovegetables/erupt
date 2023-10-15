package com.qamslink.mes.model.basic;

import com.qamslink.mes.model.equipment.MesEquipment;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "mes_product_line")
@Erupt(name = "产线定义",
        orderBy = "MesProductLine.createTime desc",
        linkTree = @LinkTree(field = "workshop", fieldClass = "MesWorkshop"),
        power = @Power(importable = true),
//        dataProxy = MesProductLineService.class,
        filter = @Filter(value = "MesProductLine.tenantId",
                params = {"and MesProductLine.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_product_line set deleted = true where id = ?")
public class MesProductLine extends HyperModelVo {

    @EruptField(
            views = @View(title = "线别名称"),
            edit = @Edit(title = "线别名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @ManyToOne
    @EruptField(
            views = @View(title = "所属车间", column = "name"),
            edit = @Edit(title = "所属车间", type = EditType.REFERENCE_TABLE, notNull = true, search = @Search(vague = true))
    )
    private MesWorkshop workshop;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述",type = EditType.TEXTAREA)
    )
    private String description;

    @ManyToMany //多对多
    @JoinTable(name = "mes_product_line_equipment",
            joinColumns = @JoinColumn(name = "product_line_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id"))
    @EruptField(
            views = @View(title = "关联设备"),
            edit = @Edit(
                    title = "关联设备",
                    type = EditType.TAB_TABLE_REFER
            )
    )
    private List<MesEquipment> equipments;
    private Boolean deleted = false;
}
