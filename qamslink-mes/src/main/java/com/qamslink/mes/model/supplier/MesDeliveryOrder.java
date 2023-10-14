package com.qamslink.mes.model.supplier;

import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.production.MesOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "mes_delivery_order")
@Setter
@Getter
@Erupt(name = "送货单",
//        dataProxy = MesDeliveryOrderService.class,
        orderBy = "MesDeliveryOrder.createTime desc",
        power = @Power(add = false, edit = false, delete = false)
//        ,
//        rowOperation = {
//                @RowOperation(
//                        title = "打印",
//                        type = RowOperation.Type.URL,
//                        urlPath = "/deliveryPrint",
//                        tplWidth = "50%"
//                )
//        }
)
@SQLDelete(sql = "update mes_delivery_order set deleted = true where id = ?")
public class MesDeliveryOrder extends TenantCreatorModel {

        @EruptField(
                views = {
                        @View(title = "送货单编号"),
                },
                edit = @Edit(title = "送货单编号",
                        search =
                        @Search(vague = true),
                        readonly = @Readonly)
        )
        private String code;


        @ManyToOne
        @EruptField(
                views = {
//                        @View(title = "采购订单客户简称", column = "supplier.alias"),
                        @View(title = "采购订单号", column = "orderCode")
                },
                edit = @Edit(title = "采购订单号", type = EditType.REFERENCE_TABLE,
                        search = @Search(vague = true),
                        referenceTableType = @ReferenceTableType(label = "orderCode"))
        )
        private MesOrder order;

        @EruptField(
                views = @View(title = "状态", sortable = true),
                edit = @Edit(title = "状态", search = @Search, notNull = true, boolType = @BoolType(
                        trueText = "已暂收", falseText = "未暂收"
                ),readonly = @Readonly)
        )
        private Boolean status = false;

        @ManyToOne
        @EruptField(
                views = @View(title = "供应商名称",column = "name"),
                edit = @Edit(title = "供应商名称", search = @Search,
                        notNull = true,type = EditType.REFERENCE_TABLE
                ,readonly = @Readonly)
        )
        private MesCustomer customer;

        @ManyToOne
        @EruptField(
                views = {
                        @View(title = "交货计划单号", column = "code")
                },
                edit = @Edit(title = "交货计划单号", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                        referenceTableType = @ReferenceTableType(label = "code"))
        )
        private MesDeliveryPlanOrder deliveryPlanOrder;

        @JoinColumn(name = "delivery_order_id")
        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
        @EruptField(
                edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
        )
        private List<MesDeliveryOrderBarcodeDetail> barcodeDetails;

        private Boolean deleted = false;
}
