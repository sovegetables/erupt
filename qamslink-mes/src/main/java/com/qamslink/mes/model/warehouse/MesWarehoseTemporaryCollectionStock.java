package com.qamslink.mes.model.warehouse;


import com.qamslink.mes.model.basic.MesStock;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "mes_warehose_temporary_collection_stock")
@Erupt(name = "仓库暂收物料明细",
//        dataProxy = MesWarehoseTemporaryCollectionStockService.class,
        orderBy = "MesWarehoseTemporaryCollectionStock.createTime desc",
        filter = @Filter(value = "MesWarehoseTemporaryCollectionStock.tenantId",
                params = {"and MesWarehoseTemporaryCollectionStock.deleted = false"},
                conditionHandler = TenantFilter.class
        )
)
public class MesWarehoseTemporaryCollectionStock extends HyperModelCreatorVo {
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
            views = {@View(title = "到货数量")},
            edit = @Edit(title = "到货数量",notNull = true)
    )
    private BigDecimal arrivalOfGoodsNum;

    @EruptField(
            views = {@View(title = "暂收数量")},
            edit = @Edit(title = "暂收数量",notNull = true)
    )
    private BigDecimal eTemporaryCollectionNum;


    @JoinColumn(name = "warehose_temporary_collection_stock_id")
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @EruptField(
            views = {
                    @View(title = "物料条码",column = "barcodeNum"),
                    @View(title = "数量",column = "capacity")
            },
            edit =@Edit(title = "物料条码",type = EditType.TAB_TABLE_ADD)

    )
    private List<MesStockBarcodePrintDetail> stockBarcodePrintDetail;
}
