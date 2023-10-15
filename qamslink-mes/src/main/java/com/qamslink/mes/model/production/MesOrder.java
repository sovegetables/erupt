package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesCustomer;
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
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mes_order")
@Setter
@Getter
@Erupt(name = "采购订单",
//        dataProxy = MesOrderService.class,
        orderBy = "MesOrder.createTime desc",
        filter = @Filter(value = "MesOrder.tenantId",
        params = {"and MesOrder.deleted = false"},
        conditionHandler = TenantFilter.class)
)
@SQLDelete(sql = "update mes_order set deleted = true where id = ?")
public class MesOrder extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views = {@View(title = "供应商名称", column = "name")},
            edit = @Edit(title = "供应商名称",notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesCustomer supplier;

    @EruptField(
            views = {@View(title = "订单号")},
            edit = @Edit(title = "订单号", notNull = true, search = @Search(vague = true))
    )
    private String orderCode;

    @EruptField(
            views = {@View(title = "日期")},
            edit = @Edit(title = "日期",
                    search = @Search(vague = true),
                    notNull = true,
                    type = EditType.DATE)
    )
    private Date orderDate;

    @EruptField(
            views = {@View(title = "订单类型")},
            edit = @Edit(title = "订单类型",
                    notNull = true,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                    vl = {@VL(label = "采购订单", value = "1"), @VL(label = "委外订单", value = "2")}
            ))
    )
    private Integer type = 1;

    @EruptField(
            views = {@View(title = "状态")},
            edit = @Edit(title = "状态",
                    readonly = @Readonly(edit = true),
                    notNull = true,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                    vl = {
                        @VL(label = "待处理", value = "1"),
                        @VL(label = "待审核", value = "2"),
                        @VL(label = "已审核", value = "3"),
                        @VL(label = "审核不通过", value = "4")

                    } )
            )
    )
    private Integer status = 1;

    @EruptField(
            views = {@View(title = "交货地址")},
            edit = @Edit(title = "交货地址", type = EditType.TEXTAREA)
    )
    private String address;

    @EruptField(
            views = {@View(title = "备注")},
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;


    @JoinColumn(name = "order_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesOrderStock> orderStocks;



    private Boolean deleted = false;

}
