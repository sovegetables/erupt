package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesStock;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.config.Comment;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物料收发明细
 */
@Getter
@Setter
@Entity
@Table(name = "mes_stock_flow_detail")
@Erupt(name = "物料收发明细",
//        dataProxy = MesStockFlowDetailService.class,
        power = @Power(add = false, edit = false, delete = false),
        orderBy = "MesStockFlowDetail.createTime desc",
        filter = @Filter(value = "MesStockFlowDetail.tenantId",
                conditionHandler = TenantFilter.class))
public class MesStockFlowDetail extends BaseModel {

    @EruptField(
            views = {@View(title = "条码编码")},
            edit = @Edit(title = "条码编码", search = @Search(vague = true))
    )
    private String barcodeNum;

    @EruptField(
            views = {@View(title = "数量")},
            edit = @Edit(title = "数量", notNull = true)
    )
    @Comment("数量（入库数量为正数，出库数量为负数）")
    private BigDecimal quantity;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "单位", column = "unit"),
            },
            edit = @Edit(title = "物料", type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "出入库类型"),
            edit = @Edit(title = "出入库类型", notNull = true, search = @Search, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "入库", value = "1"),
                            @VL(label = "出库", value = "2")
                    }
            ))
    )
    private Integer type;


    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "仓库", column = "name"),
            },
            edit = @Edit(title = "仓库", show = false, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesWarehouse warehouse;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "库位", column = "locationCode")
            },
            edit = @Edit(title = "库位", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    show = false, referenceTableType = @ReferenceTableType(label = "locationCode"))
    )
    private MesLocation location;

    @EruptField(
            views = @View(title = "单据类型"),
            edit = @Edit(title = "单据类型", notNull = true, search = @Search, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "采购入库单", value = "1"),
                            @VL(label = "生产领料单", value = "2"),
                            @VL(label = "成品出库单", value = "3"),
                            @VL(label = "成品入库单", value = "4"),
                            @VL(label = "其他入库单", value = "5"),
                            @VL(label = "其他出库单", value = "6")
                    }
            ))
    )
    private Integer orderType;

    @EruptField(
            views = {@View(title = "单据号")},
            edit = @Edit(title = "单据号", notNull = true, search = @Search(vague = true))
    )
    private String orderCode;

    @EruptField
    @Comment("租户id")
    private Long tenantId;

    @EruptField
    private Date createTime;
}
