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
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;
import xyz.erupt.upms.model.EruptUserVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mes_unit_measure_code_detail")
@Setter
@Getter
@Erupt(name = "计量单位转换",
//        dataProxy = MesUnitMeasureCodeDetailService.class,
        orderBy = "MesUnitMeasureCodeDetail.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(value = "MesUnitMeasureCodeDetail.tenantId", params = {"and MesUnitMeasureCodeDetail.deleted = false"}, conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_unit_measure_code_detail set deleted = true where id = ?")
public class MesUnitMeasureCodeDetail extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views ={@View(title = "物料名称",column = "name"),
                    @View(title = "物料编码",column = "code")},
            edit = @Edit(title = "物料",notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views ={@View(title = "转换前计量单位",column = "name"),
                    @View(title = "转换前编码",column = "unitCode")},
            edit = @Edit(title = "转换前计量单位", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesUnitMeasureCode beforeUnitMeasureCode;

    @ManyToOne
    @EruptField(
            views ={@View(title = "转换后计量单位",column = "name"),
                    @View(title = "转换后编码",column = "unitCode")},
            edit = @Edit(title = "转换后计量单位",notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesUnitMeasureCode afterUnitMeasureCode;

    @EruptField(
            views = @View(title = "转换率"),
            edit = @Edit(title = "转换率", notNull = true)
    )
    private BigDecimal conversionRate;



    private Boolean deleted = false;

    @ManyToOne
    @EruptField(
            views = @View(title = "创建人", width = "100px", column = "name"),
            edit = @Edit(title = "创建人", editShow = false , readonly = @Readonly, type = EditType.REFERENCE_TABLE)
    )
    @EruptSmartSkipSerialize
    private EruptUserVo createUser;

    @EruptField(
            views = @View(title = "创建时间", sortable = true),
            edit = @Edit(title = "创建时间", editShow = false , readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    @EruptSmartSkipSerialize
    private Date createTime;

}
