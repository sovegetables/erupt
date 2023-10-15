package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesStockProductionOut;
import com.qamslink.mes.model.production.MesCustomerOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.*;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "mes_production_out")
@Erupt(name = "成品出库单",
//        dataProxy = MesProductionOutService.class,
        orderBy = "MesProductionOut.createTime desc",
        filter = @Filter(value = "MesProductionOut.tenantId",
                params = {"and MesProductionOut.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_production_out set deleted = true where id = ?")
public class MesProductionOut extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "成品出库单号"),
            edit = @Edit(title = "成品出库单号", readonly = @Readonly, search = @Search(vague = true))
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "单位全称", column = "customer.name"),
                    @View(title = "客户订单", column = "orderCode")
            },
            edit = @Edit(title = "客户订单", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "orderCode"), readonly = @Readonly(add = false))
    )
    private MesCustomerOrder customerOrder;

    @ManyToOne
    @EruptField(
            views = @View(title = "出货单号", column = "code"),
            edit = @Edit(title = "出货单号",
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"),
                    readonly = @Readonly(add = false), search = @Search(vague = true))
    )
    private MesShipment shipment;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mes_production_out_id")
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "详情", notNull = true, type = EditType.TAB_TABLE_ADD, readonly = @Readonly)
    )
    private List<MesStockProductionOut> stockProductionOutOrders;

    @EruptField(
            views = @View(title = "出库人"),
            edit = @Edit(title = "出库人", type = EditType.INPUT, readonly = @Readonly(add = false))
    )
    private String outboundUser;

    @EruptField(
            views = @View(title = "出库时间", type = ViewType.DATE_TIME),
            edit = @Edit(title = "出库时间", readonly = @Readonly(add = false))
    )
    private Date outTime;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(
                    title = "备注",
                    type = EditType.TEXTAREA
            )
    )
    private String remark;

    

    private Boolean deleted = false;
}
