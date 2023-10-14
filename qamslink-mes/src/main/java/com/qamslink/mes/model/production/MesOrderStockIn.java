package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesGroupUser;
import com.qamslink.mes.model.basic.MesOrderStockInDetail;
import com.qamslink.mes.model.warehouse.MesWarehouse;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mes_order_stock_in")
@Setter
@Getter
@Erupt(name = "采购入库单",
//        dataProxy = MesOrderStockInService.class,
        orderBy = "MesOrderStockIn.createTime desc",
        power = @Power(delete = false, add = false,edit = false),
        filter = @Filter(value = "MesOrderStockIn.tenantId",
                params = {"and MesOrderStockIn.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_order_stock_in set deleted = true where id = ?")
public class MesOrderStockIn extends TenantCreatorModel {

    @EruptField(
            views = @View(title = "采购入库单号"),
            edit = @Edit(title = "采购入库单号", notNull = true, search = @Search(vague = true))
    )
    private String sn;

    @ManyToOne
    @EruptField(
            views = {@View(title = "采购单号", column = "orderCode")},
            edit = @Edit(title = "采购单号", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE
                    , referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesOrder order;


    @ManyToOne
    @EruptField(
            views = {@View(title = "仓库", column = "name")},
            edit = @Edit(title = "仓库", readonly = @Readonly(),
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType)
    )
    private MesWarehouse warehouse;

    @EruptField(
            views = @View(title = "收料状态", show = false),
            edit = @Edit(title = "收料状态", readonly = @Readonly(), type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "待检测", value = "0"),
                            @VL(label = "已入库", value = "1")
                    }))
    )
    private Integer status;

    @ManyToOne
    @EruptField(
            views = @View(title = "入库人", column = "name"),
            edit = @Edit(title = "入库人", readonly = @Readonly(), type = EditType.REFERENCE_TABLE)
    )
    private MesGroupUser user;

    @EruptField(
            views = @View(title = "入库时间"),
            edit = @Edit(title = "入库时间", readonly = @Readonly(), search = @Search(vague = true))
    )
    private Date receivingTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "stock_in_id")
    @EruptField(
            views = @View(title = "物料明细"),
            edit = @Edit(title = "物料明细", type = EditType.TAB_TABLE_ADD, readonly = @Readonly())
    )
    private Set<MesOrderStockInDetail> stockBarcodePrintDetails;


    @EruptField
    private Long tenantId;

    private Boolean deleted = false;

}
