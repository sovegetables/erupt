package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.production.MesCustomerOrder;
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
@Table(name = "mes_sale_return_record")
@Erupt(name = "销售退货单",
//        dataProxy = MesSaleReturnRecordService.class,
        orderBy = "MesSaleReturnRecord.createTime desc",
        filter = @Filter(value = "MesSaleReturnRecord.tenantId",
                conditionHandler = TenantFilter.class))
public class MesSaleReturnRecord extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "销售退货单号"),
            edit = @Edit(title = "销售退货单号", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @EruptField(
            views = {@View(title = "单据日期")},
            edit = @Edit(title = "单据日期", notNull = true,
                    type = EditType.DATE, search = @Search(vague = true))
    )
    private Date outDate;

    @ManyToOne
    @EruptField(
            views = @View(title = "销售订单号", column = "orderCode"),
            edit = @Edit(title = "销售订单号", type = EditType.REFERENCE_TABLE,
                    readonly = @Readonly(add = false),
                    referenceTableType = @ReferenceTableType(label = "orderCode"),
                    search = @Search(vague = true))
    )
    private MesCustomerOrder customerOrder;

    @EruptField(
            views = @View(title = "出货单号", column = "code"),
            edit = @Edit(title = "出货单号",
                    notNull = true,
                    type = EditType.REFERENCE_TABLE,
                    readonly = @Readonly(add = false),
                    referenceTableType = @ReferenceTableType(label = "code"),
                    search = @Search(vague = true))
    )
    @ManyToOne
    private MesShipment mesShipment;

    @ManyToOne
    @EruptField(
            views = @View(title = "客户", column = "name"),
            edit = @Edit(title = "客户", type = EditType.REFERENCE_TABLE,
                    readonly = @Readonly(add = false),
                    search = @Search(vague = true))
    )
    private MesCustomer customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sale_return_record_id")
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesSaleReturnRecordDetail> saleReturnRecordDetails;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态",
                    readonly = @Readonly,
                    notNull = true, boolType =
            @BoolType(trueText = "已入库", falseText = "未入库"))
    )
    private Boolean orderStatus = false;
}
