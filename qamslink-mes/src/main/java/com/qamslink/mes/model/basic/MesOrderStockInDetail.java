package com.qamslink.mes.model.basic;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "mes_order_stock_in_detail")
@Erupt(name = "采购入库明细")
public class MesOrderStockInDetail {
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "native")
    @Column(name = "id")
    @EruptField
    private Long id;

    @ManyToOne
    @EruptField(
            views = {@View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "规格型号", column = "spec")},
            edit = @Edit(title = "物料名称", notNull = true, search = @Search(vague = true),type = EditType.REFERENCE_TABLE)

    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", notNull = true, search = @Search(vague = true))
    )
    private String barcodeNum;

    @Transient
    private Long stockId;
    @Transient
    private Long stockInId;

    @EruptField(
            views = @View(title = "入库数量"),
            edit = @Edit(title = "入库数量", notNull = true, search = @Search(vague = true))
    )
    private BigDecimal capacity;
}
