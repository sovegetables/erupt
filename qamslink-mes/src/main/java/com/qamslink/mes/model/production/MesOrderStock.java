package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.basic.MesUnitMeasureCode;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name ="mes_order_stock")
@Erupt(name = "采购订单物料详情"
//        ,
//        dataProxy = MesOrderStockDataProxy.class
)
@Getter
@Setter
public class MesOrderStock extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {@View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "规格型号", column = "spec"),
                    @View(title = "单位", column = "unit")},
            edit = @Edit(title = "物料名称", notNull = true,
                    search = @Search(vague = true), type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType)
    )
    private MesStock stock;

    @EruptField(
            views = {@View(title = "数量")},
            edit = @Edit(title = "数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal num;


    @ManyToOne
    @EruptField(
            views = @View(title = "计量单位" ,column = "name"),
            edit = @Edit(title = "计量单位", notNull = true,type = EditType.REFERENCE_TREE)

    )
    private MesUnitMeasureCode unitMeasureCode;

    @EruptField(
            views = {@View(title = "单价(元)")},
            edit = @Edit(title = "单价(元)", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal price;

    @EruptField(
            views = {@View(title = "总价")},
            edit = @Edit(title = "总价", notNull = false, show = false,numberType = @NumberType(min = 0))
    )
    private BigDecimal amount;

    @EruptField(
            views = {@View(title = "交期")},
            edit = @Edit(title = "交期", notNull = true, type = EditType.DATE)
    )
    private Date endDate;

    @Transient
    @EruptField(
            views = {@View(title = "已打码数量")},
            edit = @Edit(title = "已打码数量", show = false, numberType = @NumberType(min = 0))
    )
    private BigDecimal cumulativeNum;

    @Transient
    @EruptField(
            views = {@View(title = "未打码数量")},
            edit = @Edit(title = "未打码数量", show = false, numberType = @NumberType(min = 0))
    )
    private BigDecimal uncodedNum;

    @EruptField(
            views = {@View(title = "状态")},
            edit = @Edit(title = "状态", notNull = true, type = EditType.BOOLEAN, boolType = @BoolType(trueText = "未关闭", falseText = "关闭"))
    )
    private Boolean status = true;


}
