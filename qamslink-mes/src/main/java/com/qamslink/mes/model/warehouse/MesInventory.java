package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.basic.MesStockCategory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "mes_inventory")
@Erupt(name = "库存列表",
//        dataProxy = MesInventoryService.class,
        orderBy = "MesInventory.createTime desc",
        power = @Power(add = false, delete = false),
        filter = @Filter(value = "MesInventory.tenantId",
                params = {"and MesInventory.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_warehouse set deleted = true where id = ?")
public class MesInventory extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "规格型号", column = "spec"),
                    @View(title = "主计量单位", column = "unit"),
                    @View(title = "版本", column = "version")
            },
            edit = @Edit(title = "物料", type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = @View(title = "物料分类", column = "name"),
            edit = @Edit(title = "物料分类", type = EditType.REFERENCE_TABLE, notNull = true)
    )
    private MesStockCategory stockCategory;

    @EruptField(
            views = {@View(title = "库存数量")},
            edit = @Edit(title = "库存数量", numberType = @NumberType(min = 0))
    )
    private BigDecimal inventoryQuantity;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "存放库位", column = "locationCode")
            },
            edit = @Edit(title = "存放库位", search = @Search(vague = true), notNull = true,
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"))
    )
    private MesLocation location;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "存放仓库", column = "name"),
            },
            edit = @Edit(title = "存放仓库", search = @Search, type = EditType.REFERENCE_TABLE)
    )
    private MesWarehouse mesWarehouse;



    private Boolean deleted = false;
}
