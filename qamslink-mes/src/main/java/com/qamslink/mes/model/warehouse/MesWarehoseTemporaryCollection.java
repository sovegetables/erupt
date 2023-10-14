package com.qamslink.mes.model.warehouse;


import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.supplier.MesDeliveryOrder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "mes_warehose_temporary_collection")
@Erupt(name = "仓库暂收",
//        dataProxy = MesWarehoseTemporaryCollectionService.class,
        orderBy = "MesWarehoseTemporaryCollection.createTime desc",
        filter = @Filter(value = "MesWarehoseTemporaryCollection.tenantId",
                params = {"and MesRejectionSlip.deleted = false"},
                conditionHandler = TenantFilter.class
        )
)
@SQLDelete(sql = "update mes_warehose_temporary_collection set deleted = true where id = ?")
public class MesWarehoseTemporaryCollection extends HyperModelCreatorVo {


    @OneToOne
    @EruptField(
            views = {
                    @View(title = "送货单号", column = "code")
            },
            edit = @Edit(title = "送货单号", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesDeliveryOrder deliveryOrder;

    @OneToOne
    @EruptField(
            views = {
                    @View(title = "供应商名称", column = "name")
            },
            edit = @Edit(title = "供应商名称", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "name"))
    )
    private MesCustomer supplier;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "仓库地址",column = "address"),
                    @View(title = "仓库", column = "name")
            },
            edit = @Edit(title = "仓库", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "name"))
    )
    private MesWarehouse warehouse;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "库位", column = "locationCode")
            },
            edit = @Edit(title = "库位", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "locationCode"))
    )
    private MesLocation location;

    @EruptField(
            views = {@View(title = "备注")},
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @JoinColumn(name = "warehose_temporary_collection_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            edit = @Edit(title = "暂收物料明细", type = EditType.TAB_TABLE_ADD)
    )
    private List<MesWarehoseTemporaryCollectionStock> stocks;

    private Boolean deleted = false;

    @EruptField
    private Long tenantId;
}
