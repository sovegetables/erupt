package com.qamslink.mes.model.outOrder;

import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.basic.MesStockOutArrivalRejectionOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mes_out_arrival_rejection_order")
@Setter
@Getter
@Erupt(name = "委外到货拒收单",
//        dataProxy = MesOutArrivalRejectionOrderService.class,
        orderBy = "MesOutArrivalRejectionOrder.createTime desc",
        filter = @Filter(
                value = "MesOutArrivalRejectionOrder.tenantId",
                params = {"and MesOutArrivalRejectionOrder.deleted = false"},
                conditionHandler = TenantFilter.class
        )
)
@SQLDelete(sql = "update mes_out_arrival_rejection_order set deleted = true where id = ?")
public class MesOutArrivalRejectionOrder extends TenantCreatorModel {

    @EruptField(
            views = {@View(title = "委外拒收单号")},
            edit = @Edit(title = "委外拒收单号", search = @Search(vague = true), show = false)
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "委外到货单", column = "code")
            },
            edit = @Edit(title = "委外到货单", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true), readonly = @Readonly(add = false),
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesOutArrivalOrder outArrivalOrder;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "供应商", column = "name")
            },
            edit = @Edit(title = "供应商", type = EditType.REFERENCE_TABLE, search = @Search(vague = true), readonly = @Readonly,
                    referenceTableType = @ReferenceTableType(label = "name"))
    )
    private MesCustomer supplier;

    @EruptField(
            views = {@View(title = "到货时间")},
            edit = @Edit(title = "到货时间", type = EditType.DATE, readonly = @Readonly)
    )
    private Date arrivalDate;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mes_out_arrival_rejection_order_id")
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "物料", notNull = true, type = EditType.TAB_TABLE_ADD, readonly = @Readonly(edit = false))
    )
    private Set<MesStockOutArrivalRejectionOrder> stockOutArrivalRejectionOrders;
    @EruptField(
            views = @View(title = "状态", sortable = true),
            edit = @Edit(title = "状态", search = @Search(), notNull = true,
                    boolType = @BoolType(trueText = "已关闭", falseText = "未关闭"),
                    readonly = @Readonly(edit = false))
    )
    private Boolean status = false;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(
                    title = "备注",
                    type = EditType.TEXTAREA
            )
    )
    private String remark;

    @EruptField
    private Long tenantId;

    @EruptField
    private Boolean deleted = false;

}
