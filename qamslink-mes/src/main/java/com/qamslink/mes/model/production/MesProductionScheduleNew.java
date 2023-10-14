package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesProductLine;
import com.qamslink.mes.model.basic.MesResource;
import com.qamslink.mes.model.basic.MesWorkingProcedure;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mes_production_schedule_new")
@Setter
@Getter
@Erupt(name = "排产工单")
public class MesProductionScheduleNew extends BaseModel {

    // 产线
    @ManyToOne
    @EruptField(
            views = @View(title = "产线", column = "name"),
            edit = @Edit(title = "产线", notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesProductLine productLine;

    @ManyToOne
    @EruptField(
            views = @View(title = "生产工单", column = "orderCode")
    )
    private MesWorkOrder workOrder;

    // 工序
    @ManyToOne
    @EruptField(
            views = @View(title = "工序", column = "name"),
            edit = @Edit(title = "工序", notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesWorkingProcedure workingProcedure;

    // 工位
    @ManyToOne
    @EruptField(
            views = @View(title = "工位", column = "name"),
            edit = @Edit(title = "工位", notNull = true , type = EditType.REFERENCE_TABLE)
    )
    private MesResource resource;

    // 人员
    @ManyToOne
    @EruptField(
            views = @View(title = "人员", column = "name"),
            edit = @Edit(title = "人员", notNull = true , type = EditType.REFERENCE_TABLE)
    )
    private EruptUser user;

    // 计划生产数量
    @EruptField(
            views = @View(title = "计划生产数量"),
            edit = @Edit(title = "计划生产数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal num;

    // 计划开始时间
    @EruptField(
            views = {@View(title = "计划开始时间")},
            edit = @Edit(title = "计划开始时间", notNull = true, type = EditType.DATE)
    )
    private Date startDate;

    // 计划完成时间
    @EruptField(
            views = {@View(title = "计划完成时间")},
            edit = @Edit(title = "计划完成时间", notNull = true, type = EditType.DATE)
    )
    private Date endDate;
}
