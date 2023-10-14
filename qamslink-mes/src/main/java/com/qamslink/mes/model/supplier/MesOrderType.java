package com.qamslink.mes.model.supplier;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "mes_order_type")
@Erupt(name = "订单类型")
public class MesOrderType extends BaseModel {

    @EruptField(
            views = @View(title = "名称")
    )
    private String name;

}
