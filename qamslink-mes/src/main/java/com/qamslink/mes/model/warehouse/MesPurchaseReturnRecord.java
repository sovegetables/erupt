package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.production.PurOrder;
import com.qamslink.mes.model.supplier.MesPurchaseArrivalOrder;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "mes_purchase_return_record")
@Erupt(name = "采购退货单",
//        dataProxy = MesPurchaseReturnRecordService.class,
        orderBy = "MesPurchaseReturnRecord.createTime desc"
        )
public class MesPurchaseReturnRecord extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "采购退货单号"),
            edit = @Edit(title = "采购退货单号", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @EruptField(
            views = {@View(title = "单据日期")},
            edit = @Edit(title = "单据日期", type = EditType.DATE, search = @Search(vague = true))
    )
    private Date arrivalDate;

    @ManyToOne
    @EruptField(
            views = @View(title = "采购订单号", column = "orderCode"),
            edit = @Edit(title = "采购订单号", type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "orderCode"),
                    search = @Search(vague = true))
    )
    private PurOrder purchaseOrder;

    @ManyToOne
    @EruptField(
            views = @View(title = "供应商", column = "name"),
            edit = @Edit(title = "供应商", type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesCustomer customer;

    @EruptField(
            views = @View(title = "到货单号", column = "code"),
            edit = @Edit(title = "到货单号",
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"),
                    search = @Search(vague = true))
    )
    @ManyToOne
    private MesPurchaseArrivalOrder purchaseArrivalOrder;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "purchase_return_record_id")
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesPurchaseReturnRecordDetail> purchaseReturnRecordDetails;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", readonly = @Readonly,
                    boolType = @BoolType(trueText = "已出库", falseText = "未出库"))
    )
    private Boolean orderStatus = false;
}
