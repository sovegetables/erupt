package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.config.EruptSmartSkipSerialize;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.model.EruptUserVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "mes_unit_measure_code")
@Setter
@Getter
@Erupt(name = "计量单位编码",
//        dataProxy = MesUnitMeasureCodeService.class,
        orderBy = "MesUnitMeasureCode.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(value = "MesUnitMeasureCode.tenantId", params = {"and MesUnitMeasureCode.deleted = false"}, conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_unit_measure_code set deleted = true where id = ?")
public class MesUnitMeasureCode extends BaseModel {
    @EruptField(
            views = @View(title = "名称"),
            edit = @Edit(title = "名称", notNull = true, search = @Search(vague = true))
    )
    private String name;
    @EruptField(
            views = @View(title = "计量单位编码"),
            edit = @Edit(title = "计量单位编码", notNull = true)
    )
    private String unitCode;




    private Boolean deleted = false;

    @ManyToOne
    @EruptField(
            views = @View(title = "创建人", width = "100px", column = "name"),
            edit = @Edit(title = "创建人", editShow = false, readonly = @Readonly, type = EditType.REFERENCE_TABLE)
    )
    @EruptSmartSkipSerialize
    private EruptUserVo createUser;

    @EruptField(
            views = @View(title = "创建时间", sortable = true),
            edit = @Edit(title = "创建时间", editShow = false, readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    @EruptSmartSkipSerialize
    private Date createTime;
}
