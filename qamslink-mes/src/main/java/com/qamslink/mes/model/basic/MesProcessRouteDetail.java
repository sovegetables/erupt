package com.qamslink.mes.model.basic;


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
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.model.EruptUserVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mes_process_route_detail")
@Setter
@Getter
@Erupt(name = "工艺路线详情",
//        dataProxy = MesProcessRouteDetailService.class,
        orderBy = "MesProcessRouteDetail.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(value = "MesProcessRouteDetail.tenantId", params = {"and MesProcessRouteDetail.deleted = false"}, conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_process_route_detail set deleted = true where id = ?")
public class MesProcessRouteDetail extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {@View(title = "工序名称", column = "name"),
                    @View(title = "工序编号", column = "name"),

            },
            edit = @Edit(title = "工序名称", notNull = true,
                    search = @Search(vague = true), type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType)
    )
    private MesWorkingProcedure workingProcedure;

    @ManyToOne
    @EruptField(
            views = {@View(title = "工序名称", column = "name"),
                    @View(title = "工序编号", column = "name"),

            },
            edit = @Edit(title = "工序名称", notNull = true,
                    search = @Search(vague = true), type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType)
    )
    private MesProcessRoute processRoute;

    @EruptField(
            views = @View(title = "排序(0开始)"),
            edit = @Edit(title = "排序(0开始)", notNull = true,type = EditType.NUMBER)
    )
    private BigDecimal sort;

    @ManyToOne
    @EruptField(
            views = @View(title = "创建人", width = "100px", column = "name"),
            edit = @Edit(title = "创建人",show = false, readonly = @Readonly, type = EditType.REFERENCE_TABLE)
    )
    private EruptUserVo createUser;

    @EruptField(
            views = @View(title = "创建时间", sortable = true),
            edit = @Edit(title = "创建时间", show = false, readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date createTime;

    @EruptField
    private Long tenantId;

    private Boolean deleted = false;

}


