package com.qamslink.mes.model.supplier;

import com.qamslink.mes.model.production.PurOrder;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
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
                        @View(title = "采购订单号", column = "code")
                },
                edit = @Edit(title = "采购订单号", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                        referenceTableType = @ReferenceTableType(label = "code"))
        )
        private PurOrder order;

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
                edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
        )
        private List<MesDeliveryPlanOrderStock> orderStocks;

        private String code(){
                String subCode = "JH_";
                String substring = UUID.randomUUID().toString().substring(0,10);//后缀
                return subCode+substring;
        }
}
