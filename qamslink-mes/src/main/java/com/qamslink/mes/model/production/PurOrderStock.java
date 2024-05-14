package com.qamslink.mes.model.production;

import com.qamslink.mes.core.SerialNumModel;
import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.basic.Unit;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name ="pur_order_stock")
@Erupt(name = "采购订单明细")
@Getter
@Setter
public class PurOrderStock extends SerialNumModel {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料编码", column = "code", width = "200px"),
                    @View(title = "物料名称", column = "name", width = "120px"),
                    @View(title = "规格型号", column = "spec", width = "120px"),
                    },
            edit = @Edit(title = "物料编码", notNull = true,
                    search = @Search(vague = true), type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesStock stock;

    @EruptField(
            views = {@View(title = "采购数量")},
            edit = @Edit(title = "采购数量", notNull = true, numberType = @NumberType(min = 0, step = 0.01))
    )
    private BigDecimal num;

    @ManyToOne
    @EruptField(
            views = {@View(title = "采购单位", column = "name")},
            edit = @Edit(title = "采购单位", notNull = true,
                    recommendBy = @EditRecommend(dependModel = "stock", dependModelPKey = "unit_id", dependField = "unit_name"),
                    type = EditType.REFERENCE_TABLE)
    )
    private Unit purUnit;

    @ManyToOne
    @EruptField(
            views = {@View(title = "库存单位", column = "name")},
            edit = @Edit(title = "库存单位",
                    recommendBy = @EditRecommend(dependModel = "stock", dependModelPKey = "unit_id", dependField = "unit_name"),
                    type = EditType.REFERENCE_TABLE)
    )
    private Unit stockUnit;

    @EruptField(
            views = {@View(title = "单价")},
            edit = @Edit(title = "单价", numberType = @NumberType(min = 0, step = 0.01))
    )
    private BigDecimal price;

    @EruptField(
            views = {@View(title = "金额")},
            edit = @Edit(title = "金额",
//                    readonly = @Readonly,
//                    recommendBy = @EditRecommend(dependField = "price", expr = "item.num + '*' + item.price"),
                    numberType = @NumberType(min = 0))
    )
    private BigDecimal amount;

    @EruptField(
            views = {@View(title = "交货日期", width = "200px")},
            edit = @Edit(title = "交货日期", type = EditType.DATE)
    )
    private Date endDate;

    private BigDecimal printNum;

    private BigDecimal unPrintNum;

    @EruptField(
            views = {@View(title = "业务状态", show = false)},
            edit = @Edit(title = "业务状态",
                    show = false,
                    type = EditType.BOOLEAN,
                    boolType = @BoolType(trueText = "未关闭", falseText = "关闭"))
    )
    private Boolean status = true;


}
