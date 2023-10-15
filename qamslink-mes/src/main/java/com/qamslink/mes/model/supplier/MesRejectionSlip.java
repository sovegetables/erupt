package com.qamslink.mes.model.supplier;

import com.qamslink.mes.converter.RejectionStatusConverter;
import com.qamslink.mes.model.production.MesOrder;
import com.qamslink.mes.type.RejectionSlipStatus;
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
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name ="mes_rejection_slip")
@Erupt(name = "采购到货拒收单",
//        dataProxy = MesRejectionSlipService.class,
        orderBy = "MesRejectionSlip.createTime desc",
        filter = @Filter(value = "MesRejectionSlip.tenantId",
                params = {"and MesRejectionSlip.deleted = false"},
                conditionHandler = TenantFilter.class
        ),
        power = @Power(add = false, delete = false, edit = false)
)
@SQLDelete(sql = "update mes_rejection_slip set deleted = true where id = ?")
public class MesRejectionSlip extends HyperModelVo {

    @EruptField(
        views = @View(title = "拒收单号"),
            edit = @Edit(title="拒收单号", search = @Search(vague = true),readonly = @Readonly)
    )
    private String code=code();

    @EruptField(
            views = {@View(title = "状态")},
            edit = @Edit(title = "状态",readonly = @Readonly(edit = true), notNull = true, type = EditType.CHOICE, choiceType = @ChoiceType(
                    fetchHandler = RejectionSlipStatus.Handler.class
            ))
    )
    @Convert(converter = RejectionStatusConverter.class)
    private RejectionSlipStatus status = RejectionSlipStatus.PENDING_COMMIT;

    @EruptField(
            views = {@View(title = "备注")},
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "客户简称", column = "supplier.alias"),
                    @View(title = "采购订单号", column = "orderCode")
            },
            edit = @Edit(title = "采购订单号", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesOrder order;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "送货单编号", column = "code")
            },
            edit = @Edit(title = "送货单", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesDeliveryOrder deliveryOrder;

    private Boolean deleted = false;

    @JoinColumn(name = "rejection_slip_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            edit = @Edit(title = "拒收物料明细", type = EditType.TAB_TABLE_ADD)
    )
    private List<MesRejectionSlipStock> orderStocks;

    private String code(){
        String subCode = "SH_";
        String substring = UUID.randomUUID().toString().substring(0,10);//后缀
        return subCode+substring;
    }
}
