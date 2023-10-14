package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesStock;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "mes_shipment_detail")
@Erupt(name = "出货单详情")
public class MesShipmentDetail extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {@View(title = "物料", column = "name"),
                    @View(title = "物料编码", column = "code")},
            edit = @Edit(title = "物料", notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "出货量"),
            edit = @Edit(title = "出货量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal capacity;

    @EruptField(
            views = @View(title = "已发货量"),
            edit = @Edit(title = "已发货量", numberType = @NumberType(min = 0))
    )
    private BigDecimal shippedCapacity;
}
