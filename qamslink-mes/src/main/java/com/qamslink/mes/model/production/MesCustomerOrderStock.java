package com.qamslink.mes.model.production;

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
import java.math.BigDecimal;

@Entity
@Table(name ="mes_customer_order_stock")
@Erupt(name = "物料")
@Getter
@Setter
public class MesCustomerOrderStock extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {@View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "规格型号", column = "spec"),
                    @View(title = "单位", column = "unit")},
            edit = @Edit(title = "物料名称", notNull = true, search = @Search(vague = true),type = EditType.REFERENCE_TABLE)

    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = {@View(title = "订单号", column = "orderCode"), @View(title = "客户简称", column = "customer.alias"),
                    @View(title = "id", column = "id", show = false)},
            edit = @Edit(title = "物料名称", notNull = true, search = @Search(vague = true),type = EditType.REFERENCE_TABLE)
    )
    private MesCustomerOrder customerOrder;

    @EruptField(
            views = {@View(title = "数量")},
            edit = @Edit(title = "数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal num;

    @EruptField(
            views = {@View(title = "备注")},
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;
}