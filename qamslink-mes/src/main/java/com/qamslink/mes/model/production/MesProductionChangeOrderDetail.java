package com.qamslink.mes.model.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.basic.MesResource;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.*;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mes_production_change_order_detail")
@Setter
@Getter
@Erupt(name = "生产变更单详情")
public class MesProductionChangeOrderDetail extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "生产任务号", column = "code")
            },
            edit = @Edit(title = "生产任务", notNull = true, type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesProductionScheduleDetail task;

    @ManyToOne
    @EruptField(
            views = @View(title = "原工位", column = "name"),
            edit = @Edit(title = "原工位", type = EditType.REFERENCE_TABLE)
    )
    private MesResource oldResource;

    @ManyToOne
    @EruptField(
            views = @View(title = "新工位", column = "name"),
            edit = @Edit(title = "新工位", type = EditType.REFERENCE_TABLE)
    )
    private MesResource newResource;

    @EruptField(
            views = @View(title = "原任务数量", type = ViewType.AUTO),
            edit = @Edit(title = "原任务数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal oldNum;

    @EruptField(
            views = @View(title = "新任务数量", type = ViewType.AUTO),
            edit = @Edit(title = "新任务数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal newNum;

    @EruptField(
            views = @View(title = "原计划开始时间", type = ViewType.DATE_TIME),
            edit = @Edit(title = "原计划开始时间")
    )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date oldStartTime;

    @EruptField(
            views = @View(title = "新计划开始时间", type = ViewType.DATE_TIME),
            edit = @Edit(title = "新计划开始时间")
    )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date newStartTime;

    @EruptField(
            views = @View(title = "原计划结束时间", type = ViewType.DATE_TIME),
            edit = @Edit(title = "原计划结束时间")
    )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date oldEndTime;

    @EruptField(
            views = @View(title = "新计划结束时间", type = ViewType.DATE_TIME),
            edit = @Edit(title = "新计划结束时间")
    )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date newEndTime;

    @ManyToOne
    @EruptField(
            views = {@View(title = "原员工姓名", column = "name"), @View(title = "原员工工号", column = "empno")},
            edit = @Edit(title = "原员工",
                    type = EditType.REFERENCE_TABLE)
    )
    private EruptUser oldUser;

    @ManyToOne
    @EruptField(
            views = {@View(title = "新员工姓名", column = "name"), @View(title = "新员工工号", column = "empno")},
            edit = @Edit(title = "新员工",
                    type = EditType.REFERENCE_TABLE)
    )
    private EruptUser newUser;

    @EruptField(
            views = @View(title = "原生产方式"),
            edit = @Edit(title = "原生产方式", readonly = @Readonly, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "自制", value = "1"),
                            @VL(label = "委外", value = "2"),
                    }
            ))
    )
    private Integer oldProductionWay;

    @EruptField(
            views = @View(title = "新生产方式"),
            edit = @Edit(title = "新生产方式", readonly = @Readonly, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "自制", value = "1"),
                            @VL(label = "委外", value = "2"),
                    }
            ))
    )
    private Integer newProductionWay;

    @ManyToOne
    @EruptField(
            views = {@View(title = "原供应商", column = "name")},
            edit = @Edit(title = "原供应商", type = EditType.REFERENCE_TABLE, search = @Search)
    )
    private MesCustomer oldSupplier;

    @ManyToOne
    @EruptField(
            views = {@View(title = "新供应商", column = "name")},
            edit = @Edit(title = "新供应商", type = EditType.REFERENCE_TABLE, search = @Search)
    )
    private MesCustomer newSupplier;

}
