package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "mes_stock_out_arrival_order")
@Setter
@Getter
@Erupt(name = "委外到货物料详情")
public class MesStockOutArrivalOrder extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "规格型号", column = "spec"),
                    @View(title = "单位", column = "unit")
            },
            edit = @Edit(title = "物料", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),readonly = @Readonly)
    )
    private MesStock stock;

    @EruptField(
            views = {@View(title = "到货数量")},
            edit = @Edit(title = "到货数量", notNull = true, numberType = @NumberType(min = 0),readonly = @Readonly)
    )
    private BigDecimal arrivalQuantity;

    @EruptField(
            views = {@View(title = "入库数量")},
            edit = @Edit(title = "入库数量", notNull = true, numberType = @NumberType(min = 0),readonly = @Readonly)
    )
    private BigDecimal receiptQuantity;

    @EruptField(
            views = {@View(title = "已拒收数量")},
            edit = @Edit(title = "已拒收数量", notNull = true, numberType = @NumberType(min = 0),readonly = @Readonly)
    )
    private BigDecimal rejectQuantity;

}