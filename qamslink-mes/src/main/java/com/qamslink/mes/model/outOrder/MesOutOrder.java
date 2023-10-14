package com.qamslink.mes.model.outOrder;

import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.basic.MesStockOutOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author LiYongGui
 * @create 2023-06-06 上午11:28
 */

@Entity
@Table(name = "mes_out_order")
@Setter
@Getter
@Erupt(name = "委外工单",
//        dataProxy = MesOutOrderService.class,
        orderBy = "MesOutOrder.createTime desc",
        filter = @Filter(
                value = "MesOutOrder.tenantId",
                params = {"and MesOutOrder.deleted = false"},
                conditionHandler = TenantFilter.class
        ),
        rowOperation = {
                @RowOperation(
                        code = "generate",
                        title = "生成委外发料单",
                        mode = RowOperation.Mode.MULTI
//                        ,
//                        operationHandler = OutOrderHandler.class
                )
        }
)
@SQLDelete(sql = "update mes_out_order set deleted = true where id = ?")
public class MesOutOrder extends TenantCreatorModel {

        @EruptField(
                views = {@View(title = "委外工单号")},
                edit = @Edit(title = "委外工单号", notNull = true, search = @Search(vague = true))
        )
        private String code;

        @ManyToOne
        @EruptField(
                views = {
                        @View(title = "供应商简称", column = "alias"),
                        @View(title = "供应商编号", column = "code")
                },
                edit = @Edit(title = "供应商", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                        referenceTableType = @ReferenceTableType(label = "alias"))
        )
        private MesCustomer customer;

        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "mes_out_order_id")
        @EruptField(
                views = @View(title = "产品详情"),
                edit = @Edit(title = "产品详情", notNull = true, type = EditType.TAB_TABLE_ADD)
        )
        private Set<MesStockOutOrder> stockOutOrders;

        @EruptField(
                views = @View(title = "状态"),
                edit = @Edit(title = "状态", type = EditType.CHOICE, notNull = true,
                        choiceType = @ChoiceType(vl = {
                                @VL(label = "未完成", value = "0"),
                                @VL(label = "已完成", value = "1")
                        }))
        )
        private Integer type = 0;

        @EruptField(
                views = @View(title = "是否需要原材料"),
                edit = @Edit(title = "是否需要原材料", notNull = true, boolType = @BoolType(trueText = "是", falseText = "否"))
        )
        private Boolean isProvideStock = false;

        @EruptField(
                views = {@View(title = "计划开始时间")},
                edit = @Edit(title = "计划开始时间", notNull = true, type = EditType.DATE)
        )
        private Date startDate;

        @EruptField(
                views = {@View(title = "计划完成时间")},
                edit = @Edit(title = "计划完成时间", notNull = true, type = EditType.DATE)
        )
        private Date endDate;
        @EruptField
        private Boolean deleted = false;

}
