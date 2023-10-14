package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesWorkingProcedure;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author LiYongGui
 * @create 2023-08-08 上午10:05
 */

@Entity
@Setter
@Getter
@Erupt(name = "排序详情",
//        dataProxy = MesWorkOrderProcedureService.class,
        orderBy = "MesWorkOrderProcedure.createTime desc",
        filter = @Filter(
                conditionHandler = TenantFilter.class
        )
)
public class MesWorkOrderProcedure extends HyperModelCreatorVo {

    private static final long serialVersionUID = 2485946002815056302L;

    @ManyToOne
    @JoinColumn(name = "working_procedure_id")
    @EruptField(
            views = {
                    @View(title = "工序", column = "name")
            },
            edit = @Edit(title = "工序", type = EditType.REFERENCE_TABLE, search = @Search(vague = true), notNull = true)
    )
    private MesWorkingProcedure workingProcedure;

    @EruptField(
            views = @View(title = "排序"),
            edit = @Edit(title = "排序", notNull = true)
    )
    private Integer sort;
    private Boolean deleted = false;
}
