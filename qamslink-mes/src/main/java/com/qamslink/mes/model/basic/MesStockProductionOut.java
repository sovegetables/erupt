package com.qamslink.mes.model.basic;

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
@Table(name = "mes_stock_production_out")
@Setter
@Getter
@Erupt(name = "成品出库物料详情")
public class MesStockProductionOut extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "规格型号", column = "spec"),
                    @View(title = "单位", column = "unit")
            },
            edit = @Edit(title = "物料", type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", notNull = true)
    )
    private String barcode;

    @EruptField(
            views = @View(title = "出库数量"),
            edit = @Edit(title = "出库数量", numberType = @NumberType(min = 0))
    )
    private BigDecimal capacity;

}
