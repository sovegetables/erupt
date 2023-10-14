package com.qamslink.mes.model.supplier;


import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "mes_order_delivery_stock")
@Setter
@Getter
@Erupt(name = "订单物料详情",
//        dataProxy = MesDeliveryPlanOrderService.class,
        filter = @Filter(value = "MesDeliveryPlanOrder.tenantId",
                conditionHandler = TenantFilter.class
        )
)
public class MesOrderDeliveryStock extends HyperModelCreatorVo {

        @EruptField(
                views = {
                        @View(title = "采购订单id"),
                },
                edit = @Edit(title = "采购订单id", show = false)
        )
        private Long orderId;

        @EruptField(
                views = {
                        @View(title = "送货计划单id"),
                },
                edit = @Edit(title = "送货计划单id", show = false)
        )
        private Long deliveryOrder;



        @EruptField(
                views = {
                        @View(title = "物料Id"),
                },
                edit = @Edit(title = "物料Id", show = false)
        )
        private Long StocksId;
}
