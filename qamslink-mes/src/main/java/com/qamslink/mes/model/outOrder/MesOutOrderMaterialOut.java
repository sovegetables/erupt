package com.qamslink.mes.model.outOrder;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.*;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "mes_out_order_material_out")
@Erupt(name = "委外发料单",
//        dataProxy = MesOutOrderMaterialOutService.class,
        orderBy = "MesOutOrderMaterialOut.createTime desc",
        filter = @Filter(value = "MesOutOrderMaterialOut.tenantId",
                params = {"and MesOutOrderMaterialOut.deleted = false"},
                conditionHandler = TenantFilter.class),
        power = @Power(add = false, delete = false))
@SQLDelete(sql = "update mes_out_order_material_out set deleted = true where id = ?")
public class MesOutOrderMaterialOut extends TenantCreatorModel {
    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "委外发料工单号",column = "code")
            },
            edit = @Edit(title = "委外发料工单号", notNull = true, readonly = @Readonly, type = EditType.REFERENCE_TABLE,
                    search = @Search, referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesOutOrderMaterial outOrderMaterial;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "委外工单号",column = "code")
            },
            edit = @Edit(title = "委外工单号", notNull = true, readonly = @Readonly, type = EditType.REFERENCE_TABLE,
                    search = @Search, referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesOutOrder outOrder;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "out_order_material_out_id")
    @EruptField(
            views = @View(title = "物料"),
            edit = @Edit(title = "物料", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesOutOrderMaterialDetail> outOrderMaterialOutDetails;

    @EruptField(
            views = @View(title = "出库时间",type = ViewType.DATE_TIME),
            edit = @Edit(title = "出库时间", show = false, search = @Search(vague = true))
    )
    private Date outTime;

    @EruptField(
            views = @View(title = "出库人员",type = ViewType.TEXT)
    )
    private String depositor;

    @EruptField(
            views = @View(title = "租户", show = false)
    )
    private Long tenantId;

    private Boolean deleted = false;
}
