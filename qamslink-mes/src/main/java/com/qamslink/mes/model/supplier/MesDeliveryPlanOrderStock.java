package com.qamslink.mes.model.supplier;

import com.qamslink.mes.model.basic.MesStock;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name ="mes_delivery_plan_order_stock")
@Erupt(name = "交货计划单物料详情"
//        ,dataProxy = MesDeliveryPlanOrderStockService.class
)
@Getter
@Setter
public class MesDeliveryPlanOrderStock extends BaseModel {

    private static final long serialVersionUID = -8917808870606505985L;
    @ManyToOne
    @EruptField(
            views = {@View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "规格型号", column = "spec"),
                    @View(title = "单位", column = "unit")},
            edit = @Edit(title = "物料名称", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = {@View(title = "订单总量")},
            edit = @Edit(title = "订单总量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal orderTotalQuantity;

    @EruptField(
            views = {@View(title = "总价")},
            edit = @Edit(title = "总价", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal amount;

    @EruptField(
            views = {@View(title = "单价")},
            edit = @Edit(title = "单价", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal price;

    @EruptField(
            views = {@View(title = "数量")},
            edit = @Edit(title = "数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal num;

    @EruptField(
            views = {@View(title = "已计划交货量")},
            edit = @Edit(title = "已计划交货量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal PlanNum;

    @Transient
    @EruptField(
            views = {@View(title = "待计划交货量")},
            edit = @Edit(title = "待计划交货量", numberType = @NumberType(min = 0))
    )
    private BigDecimal toBePlanNum;

    @Transient
    @EruptField(
            views = {@View(title = "最终交期")},
            edit = @Edit(title = "最终交期", type = EditType.DATE)
    )
    private Date endDate;

    @EruptField
    private Long tenantId;
}
