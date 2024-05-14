package com.qamslink.mes.model.quality;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mes_aql_lot_size")
@Getter
@Setter
@Erupt(name = "批量检验水平",
        orderBy = "MesAQLLotSize.id asc",
        power = @Power(importable = true),
        filter = @Filter(conditionHandler = TenantFilter.class))
public class MesAQLLotSize extends BaseModel {

    @EruptField(
            views = @View(title = "LotSize_Min", sortable = true),
            edit = @Edit(title = "LotSize_Min", notNull = true)
    )
    private String lotSizeMin;

    @EruptField(
            views = @View(title = "LotSize_Max"),
            edit = @Edit(title = "LotSize_Max", notNull = true)
    )
    private String lotSizeMax;

    @EruptField(
            views = @View(title = "Audit_S1"),
            edit = @Edit(title = "Audit_S1", notNull = true)
    )
    private String auditS1;

    @EruptField(
            views = @View(title = "Audit_S2"),
            edit = @Edit(title = "Audit_S2", notNull = true)
    )
    private String auditS2;

    @EruptField(
            views = @View(title = "Audit_S3"),
            edit = @Edit(title = "Audit_S3", notNull = true)
    )
    private String auditS3;

    @EruptField(
            views = @View(title = "Audit_S4"),
            edit = @Edit(title = "Audit_S4", notNull = true)
    )
    private String auditS4;
    @EruptField(
            views = @View(title = "Audit_I"),
            edit = @Edit(title = "Audit_I", notNull = true)
    )
    private String auditI;

    @EruptField(
            views = @View(title = "Audit_II"),
            edit = @Edit(title = "Audit_II", notNull = true)
    )
    private String auditII;
    @EruptField(
            views = @View(title = "Audit_III"),
            edit = @Edit(title = "Audit_III", notNull = true)
    )
    private String auditIII;
}
