package com.qamslink.mes.model.supplier;

import com.qamslink.mes.model.production.MesOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "mes_delivery_plan_order")
@Setter
@Getter
@Erupt(name = "交货计划单",
//        dataProxy = MesDeliveryPlanOrderService.class,
        orderBy = "MesDeliveryPlanOrder.createTime desc",
        filter = @Filter(value = "MesDeliveryPlanOrder.tenantId",
                params = {"and MesDeliveryPlanOrder.deleted = false"},
                conditionHandler = TenantFilter.class
        ),
        rowOperation = {
                @RowOperation(
                        code = "commit",
                        title = "提交",
                        mode = RowOperation.Mode.MULTI
//                        ,
//                        operationHandler = DeliveryPlanOrderHandler.class
                )
        }
)
@SQLDelete(sql = "update mes_delivery_plan_order set deleted = true where id = ?")
public class MesDeliveryPlanOrder extends HyperModelVo {


        @EruptField(
                views = {
                        @View(title = "交货单编号"),
                },
                edit = @Edit(title = "交货单编号", show = false, search = @Search(vague = true),readonly = @Readonly)
        )
        private String code = code();

        @ManyToOne
        @EruptField(
                views = {
                        @View(title = "客户简称", column = "supplier.alias"),
                        @View(title = "采购订单号", column = "orderCode")
                },
                edit = @Edit(title = "采购订单号", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                        referenceTableType = @ReferenceTableType(label = "orderCode"))
        )
        private MesOrder order;

        @EruptField(
                views = @View(title = "状态", sortable = true),
                edit = @Edit(title = "状态", readonly = @Readonly, search = @Search, notNull = true, boolType = @BoolType
                        (
                                trueText = "已提交", falseText = "未提交"
                        )
                )
        )
        private Boolean status = false;


        @JoinColumn(name = "delivery_plan_order_id")
        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
        @EruptField(
                edit = @Edit(title = "物料(不填物料默认是采购订单的物料)", type = EditType.TAB_TABLE_ADD)
        )
        private List<MesDeliveryPlanOrderStock> orderStocks;

        private Boolean deleted = false;

        private String code(){
                String subCode = "JH_";
                String substring = UUID.randomUUID().toString().substring(0,10);//后缀
                return subCode+substring;
        }
}
