package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesCustomer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_erupt.Tpl;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mes_customer_order")
@Setter
@Getter
@Erupt(name = "客户订单",
//        dataProxy = MesCustomerOrderService.class,
        orderBy = "MesCustomerOrder.createTime desc",
        filter = @Filter(
                value = "MesCustomerOrder.tenantId",
                params = {"and MesCustomerOrder.deleted = false"},
                conditionHandler = TenantFilter.class
        ),
        rowOperation = {
                @RowOperation(
                        title = "生成生产工单",
                        mode = RowOperation.Mode.MULTI,
//                        operationHandler = CustomerOrderHandler.class,
                        type = RowOperation.Type.TPL,
                        tpl = @Tpl(
                                path = "/tpl/index.html"
                        )
//                        ,
//                        tplWidth = "70%"
                )
        }
)
@SQLDelete(sql = "update mes_customer_order set deleted = true where id = ?")
public class MesCustomerOrder extends HyperModelCreatorVo {
    @ManyToOne
    @EruptField(
            views = {@View(title = "客户名称", column = "name")},
            edit = @Edit(title = "客户名称",notNull = true,search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesCustomer customer;

    @EruptField(
            views = {@View(title = "订单号")},
            edit = @Edit(title = "订单号", search = @Search(vague = true), notNull = true)
    )
    private String orderCode;

    @EruptField(
            views = {@View(title = "单据日期")},
            edit = @Edit(title = "单据日期", notNull = true, type = EditType.DATE)
    )
    private Date orderDate;

    @EruptField(
            views = {@View(title = "状态")},
            edit = @Edit(title = "状态", notNull = true,search = @Search(vague = true), type = EditType.BOOLEAN, boolType = @BoolType(trueText = "未关闭", falseText = "关闭"))
    )
    private Boolean status = true;

    @EruptField(
            views = {@View(title = "备注")},
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;


    @JoinColumn(name = "customer_order_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "产品"),
            edit = @Edit(title = "产品", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesCustomerOrderStockVO> customerOrderStocks;


    @EruptField
    private Long tenantId;

    private Boolean deleted = false;

}
