package com.qamslink.mes.model.production;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mes_production_change_order")
@Setter
@Getter
@Erupt(name = "生产变更单",
//        dataProxy = MesProductionChangeOrderService.class,
        orderBy = "MesProductionChangeOrder.id desc",
        filter = @Filter(value = "MesProductionChangeOrder.tenantId",
                conditionHandler = TenantFilter.class),
        power = @Power(add = false, edit = false, delete = false)
)
public class MesProductionChangeOrder extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "生产工单号", column = "orderCode")
            },
            edit = @Edit(title = "生产工单号", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @ManyToOne
    @EruptField(
            views = {@View(title = "创建人", column = "name")},
            edit = @Edit(title = "创建人",
                    type = EditType.REFERENCE_TABLE)
    )
    private EruptUser createUser;

    @EruptField(
            views = @View(title = "变更时间", type = ViewType.DATE_TIME)
    )
    private Date createTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "production_change_order_id")
    @EruptField(
            edit = @Edit(title = "详情", type = EditType.TAB_TABLE_ADD)
    )
    private List<MesProductionChangeOrderDetail> details;

    @EruptField
    private Long tenantId;

}
