package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author LiYongGui
 * @create 2023-06-06 下午5:20
 * 委外订单使用
 */

@Entity
@Table(name = "mes_stock_outorder")
@Setter
@Getter
@Erupt(name = "产品详情")
public class MesStockOutOrder extends BaseModel {

    @EruptField(
            views = @View(title = "产品数量"),
            edit = @Edit(title = "产品数量", type = EditType.INPUT)
    )
    private BigDecimal scheduledNum;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "产品名称", column = "name"),
                    @View(title = "产品编号", column = "code")
            },
            edit = @Edit(title = "产品名称", type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.INPUT)
    )
    private String remark;
}
