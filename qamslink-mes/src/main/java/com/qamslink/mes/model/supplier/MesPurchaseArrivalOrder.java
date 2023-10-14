package com.qamslink.mes.model.supplier;

import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.production.MesOrder;
import com.qamslink.mes.model.warehouse.MesLocation;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
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
@Table(name = "mes_purchase_arrival_order")
@Setter
@Getter
@Erupt(name = "采购到货单",
//        dataProxy = MesPurchaseArrivalOrderService.class,
        orderBy = "MesPurchaseArrivalOrder.createTime desc",
        filter = @Filter(value = "MesPurchaseArrivalOrder.tenantId",
                params = {"and MesPurchaseArrivalOrder.deleted = false"},
                conditionHandler = TenantFilter.class
        ),
        power = @Power(add = false, edit = false, delete = false)
)
@SQLDelete(sql = "update mes_purchase_arrival_order set deleted = true where id = ?")
public class MesPurchaseArrivalOrder extends TenantCreatorModel {

    @EruptField(
            views = {
                    @View(title = "到货单号"),
            },
            edit = @Edit(title = "到货单号", show = false, search = @Search(vague = true), readonly = @Readonly)
    )
    private String code;


    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "采购订单号", column = "orderCode")
            },
            edit = @Edit(title = "采购订单", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesOrder order;

    @ManyToOne
    @EruptField(
            views = @View(title = "供应商", column = "name"),
            edit = @Edit(title = "供应商", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesCustomer supplier;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "送货单号", column = "code")
            },
            edit = @Edit(title = "送货单", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesDeliveryOrder deliveryOrder;

    @EruptField(
            views = {@View(title = "到货时间")},
            edit = @Edit(title = "到货时间", notNull = true, type = EditType.DATE)
    )
    private Date arrivalDate;

    @JoinColumn(name = "purchase_arrival_order_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = {@View(title = "物料")},
            edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesPurchaseArrivalOrderStock> stockDetails;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "暂存地点", column = "locationCode")
            },
            edit = @Edit(title = "暂存地点", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "locationCode"))
    )
    private MesLocation mesLocation;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
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
            edit = @Edit(title = "状态", search = @Search, notNull = true,
                    boolType = @BoolType(trueText = "已入库", falseText = "未入库"),
                    readonly = @Readonly)
    )
    private Boolean status = false;

    private Boolean deleted = false;

    @EruptField
    private Long tenantId;

}
