package com.qamslink.mes.model.basic;

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
@Table(name = "mes_customer_type")
@Erupt(name = "客户类型")
public class MesCustomerType extends BaseModel {

    public static final int CODE_CLIENT_ID = 1;
    public static final int CODE_SUPPER_ID = 2;
    public static final int CODE_BOTH_ID = 3;

    public static final String CODE_SUPPER = "supplier";
    public static final String CODE_CLIENT = "client";
    public static final String CODE_BOTH = "both";

    @EruptField(
            views = @View(title = "名称")
    )
    private String name;

    @EruptField(
            views = @View(title = "编码")
    )
    private String code;

}
