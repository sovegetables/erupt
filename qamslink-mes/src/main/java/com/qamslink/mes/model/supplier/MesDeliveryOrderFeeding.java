package com.qamslink.mes.model.supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Table(name ="mes_delivery_order_stock")
@Erupt(name = "送货单物料详情")
@Getter
@Setter
public class MesDeliveryOrderFeeding extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {@View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code")},
            edit = @Edit(title = "物料名称", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = {@View(title = "数量")},
            edit = @Edit(title = "数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal num;

    @JoinColumn(name = "delivery_order_stock_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "明细"),
            edit = @Edit(title = "明细", type = EditType.TAB_TABLE_ADD)
    )
    @JsonIgnore
    private List<MesDeliveryOrderBarcodeDetail> deliveryOrderBarcodeDetailList;

    @EruptField(
            views = {@View(title = "交期")},
            edit = @Edit(title = "交期", notNull = true, type = EditType.DATE)
    )
    private Date endDate;

    @EruptField(
            views = {@View(title = "已送货量")},
            edit = @Edit(title = "已送货量", notNull = true, type = EditType.NUMBER)
    )
    private BigDecimal shippedCapacity;


}

