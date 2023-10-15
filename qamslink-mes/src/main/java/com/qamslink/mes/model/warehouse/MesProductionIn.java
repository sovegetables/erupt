package com.qamslink.mes.model.warehouse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
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
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "mes_production_in")
@Erupt(name = "成品入库",
        orderBy = "MesProductionIn.createTime desc",
        filter = @Filter(value = "MesProductionIn.tenantId",
                conditionHandler = TenantFilter.class))
public class MesProductionIn extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "入库单号"),
            edit = @Edit(title = "入库单号", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = {@View(title = "库位", column = "warehouse.code",show = false),
                    @View(title = "库位", column = "storageSiteCode",show = false),
                    @View(title = "库位", column = "locationCode",show = false),
                    @View(title = "库位", column = "locationCode",template = "item.location_warehouse_code + '-' + item.location_storageSiteCode + '-' + item.location_locationCode"),
            },
            edit = @Edit(title = "库位", notNull = true, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"))
    )
    private MesLocation location;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @JsonIgnore
    @JoinColumn(name = "production_in_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(title = "详情", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesProductionInDetail> productionInDetails;


}
