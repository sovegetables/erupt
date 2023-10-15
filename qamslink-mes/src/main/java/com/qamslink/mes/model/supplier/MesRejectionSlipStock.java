package com.qamslink.mes.model.supplier;

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
@Table(name ="mes_rejection_slip_stock")
@Erupt(name = "拒收单物料详情"
//        , dataProxy = MesRejectionSlipStockService.class
)
@Getter
@Setter
public class MesRejectionSlipStock extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {@View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "规格型号", column = "spec"),
                    @View(title = "单位", column = "unit")},
            edit = @Edit(title = "物料名称", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = {@View(title = "送货量")},
            edit = @Edit(title = "送货量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal deliveryNum;

    @EruptField(
            views = {@View(title = "数量")},
            edit = @Edit(title = "数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal num;

    @EruptField(
            views = {@View(title = "收货量")},
            edit = @Edit(title = "收货量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal amount;

    @EruptField(
            views = {@View(title = "拒收量")},
            edit = @Edit(title = "拒收量", numberType = @NumberType(min = 0))
    )
    private BigDecimal rejectionNum;

    @EruptField(
            views = {@View(title = "拒收说明")},
            edit = @Edit(title = "拒收说明", type = EditType.TEXTAREA)
    )
    private String remark;


}
