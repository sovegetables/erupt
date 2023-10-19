package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.production.MesCustomerOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "mes_shipment")
@Erupt(name = "销售出货单",
//        dataProxy = MesShipmentService.class,
        orderBy = "MesShipment.createTime desc",
        filter = @Filter(value = "MesShipment.tenantId",
                params = {"and MesShipment.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_shipment set deleted = true where id = ?")
public class MesShipment extends HyperModel {

    @EruptField(
            views = @View(title = "出货单号"),
            edit = @Edit(title = "出货单号", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = @View(title = "客户订单号", column = "orderCode"),
            edit = @Edit(title = "客户订单号", search = @Search(vague = true), type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesCustomerOrder customerOrder;

    @EruptField(
            views = @View(title = "计划出货时间"),
            edit = @Edit(title = "计划出货时间", dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date planTime;

    @Transient
    @EruptField(
            views = @View(title = "出库人"),
            edit = @Edit(title = "出库人",  search = @Search(vague = true), show = false, type = EditType.INPUT)
    )
    private String outboundUser;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", type = EditType.CHOICE, readonly = @Readonly(),search = @Search(vague = false),
                    choiceType = @ChoiceType (
                        vl = {
                                @VL(label = "未出库", value = "0"),
                                @VL(label = "部分出库", value = "1"),
                                @VL(label = "全部出库", value = "2")
                        }
                    )
            )
    )
    private Integer status = 0;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shipment_id")
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesShipmentDetail> shipmentDetails;

    @EruptField(
            views = @View(title = "租户", show = false, columnShowed = false)
    )


    private Boolean deleted = false;
}
