package com.qamslink.mes.model.outOrder;

import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.basic.MesStockOutArrivalOrder;
import com.qamslink.mes.model.warehouse.MesLocation;
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
@Table(name = "mes_out_arrival_order")
@Setter
@Getter
@Erupt(name = "委外到货单",
//        dataProxy = MesOutArrivalOrderService.class,
        orderBy = "MesOutArrivalOrder.createTime desc",
        filter = @Filter(
                value = "MesOutArrivalOrder.tenantId",
                params = {"and MesOutArrivalOrder.deleted = false"},
                conditionHandler = TenantFilter.class
        )
)
@SQLDelete(sql = "update mes_out_arrival_order set deleted = true where id = ?")
public class MesOutArrivalOrder extends TenantCreatorModel {

    @EruptField(
            views = {@View(title = "到货单号")},
            edit = @Edit(title = "到货单号", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "委外工单号", column = "code")
            },
            edit = @Edit(title = "委外工单号", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesOutOrder outOrder;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "供应商", column = "name")
            },
            edit = @Edit(title = "供应商", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "name"))
    )
    private MesCustomer supplier;

    @EruptField(
            views = {@View(title = "到货时间")},
            edit = @Edit(title = "到货时间", notNull = true, type = EditType.DATE, search = @Search(vague = true))
    )
    private Date arrivalDate;



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mes_out_arrival_order_id")
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "物料", notNull = true, type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesStockOutArrivalOrder> stockOutArrivalOrder;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "暂存地点", column = "locationCode")
            },
            edit = @Edit(title = "暂存地点", type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "locationCode"))
    )
    private MesLocation mesLocation;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(
                    title = "备注",
                    type = EditType.TEXTAREA
            )
    )
    private String remark;

    @EruptField(
            views = @View(title = "来料检验状态"),
            edit = @Edit(title = "来料检验状态",
                    boolType = @BoolType(falseText = "未检验", trueText = "已检验"),
                    readonly = @Readonly)
    )
    private Boolean iqcInspection = false;

    @EruptField(
            views = @View(title = "状态", sortable = true),
            edit = @Edit(title = "状态", search = @Search(), notNull = true,
                    boolType = @BoolType(trueText = "已入库", falseText = "未入库"),
                    readonly = @Readonly)
    )
    private Boolean status = false;

    @EruptField
    private Long tenantId;
    @EruptField
    private Boolean deleted = false;

}
