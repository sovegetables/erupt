package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesWorkshop;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "mes_product_line")
@Erupt(name = "产线",
//        dataProxy = MesProductionScheduleDetailProductionLineService.class,
        filter = @Filter(
                value = "MesProductionScheduleDetailProductionLine.tenantId",
                params = {"and MesProductionScheduleDetailProductionLine.deleted = false"},
                conditionHandler = TenantFilter.class
        )
)
public class MesProductionScheduleDetailProductionLine extends BaseModel {

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
            edit = @Edit(title = "描述")
    )
    private String description;




    private Boolean deleted;

}
