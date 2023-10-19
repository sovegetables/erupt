package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.basic.MesProductLine;
import com.qamslink.mes.model.basic.MesResource;
import com.qamslink.mes.model.basic.MesWorkingProcedure;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.RowOperation;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "is_down", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("0")
@Table(name = "mes_production_schedule_detail")
@Setter
@Getter
@Erupt(name = "生产任务",
//        dataProxy = MesProductionScheduleDetailService.class,
        orderBy = "MesProductionScheduleDetail.id desc",
        filter = @Filter(value = "MesProductionScheduleDetail.tenantId",
                params = {"and MesProductionScheduleDetail.deleted = false"},
                conditionHandler = TenantFilter.class),
        power = @Power(add = false, delete = false),
        rowOperation = {
                @RowOperation(
                        title = "下发MES"
//                        ,
//                        operationHandler = MesProductionScheduleDetailService.class
                ),
        }
)
@SQLDelete(sql = "update mes_production_schedule_detail set deleted = true where id = ?")
public class MesProductionScheduleDetail extends BaseModel {

    public static final int STATUS_UN_START = 0;
    public static final int STATUS_STARTING = 1;
    public static final int STATUS_FINISH = 2;
    public static final int STATUS_ABORT = 3;

    @EruptField(
            views = @View(title = "任务编号"),
            edit = @Edit(title = "任务编号", readonly = @Readonly, search = @Search, show = false)
    )
    private String code;

    @OneToOne
    @EruptField(views = @View(title = "下任务编号", column = "code", show = false)
    )
    private MesProductionScheduleDetail nextProcedureTask;

    @OneToOne
    @EruptField(
       views = @View(title = "上任务编号", column = "code", show = false)
    )
    private MesProductionScheduleDetail previousProcedureTask;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", show = false, type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "未开始", value = STATUS_UN_START + ""),
                            @VL(label = "进行中", value = STATUS_STARTING + ""),
                            @VL(label = "已完成", value = STATUS_FINISH + ""),
                            @VL(label = "终止", value = STATUS_ABORT + "")
                    }), search = @Search)
    )
    private Integer status = 0;

    @EruptField(
            views = @View(title = "终止状态"),
            edit = @Edit(title = "终止状态", readonly = @Readonly(), type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "未终止", value = "1"),
                            @VL(label = "已终止", value = "2")
                    }
            ))
    )
    private Integer finishStatus = 1;

    @EruptField(
            views = @View(title = "工序状态"),
            edit = @Edit(title = "工序状态", readonly = @Readonly, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "正常", value = "0"),
                            @VL(label = "工序跳转", value = "1"),
                            @VL(label = "提前完工", value = "2")
                    }
            ))
    )
    private Integer taskWorkProcedureStatus = 0;

    @EruptField(
            views = @View(title = "生产方式"),
            edit = @Edit(title = "生产方式", readonly = @Readonly, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "自制", value = "1"),
                            @VL(label = "委外", value = "2"),
                    }
            ), search = @Search)
    )
    private Integer productionWay = 1;

    @ManyToOne
    @EruptField(
            views = {@View(title = "供应商", column = "name")},
            edit = @Edit(title = "供应商", type = EditType.REFERENCE_TABLE, search = @Search)
    )
    private MesCustomer supplier;

    @ManyToOne
    @EruptField(
            views = {@View(title = "工位", column = "name")},
            edit = @Edit(title = "工位", search = @Search, type = EditType.REFERENCE_TABLE, notNull = true)
    )
    private MesResource resource;

    @ManyToOne
    @EruptField(
            views = @View(title = "产线", column = "name", show = false),
            edit = @Edit(title = "产线", show = false)
    )
    private MesProductLine mesProductLine;

    @ManyToOne
    @EruptField(
            views = @View(title = "工序", column = "name"),
            edit = @Edit(title = "工序", show = false, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesWorkingProcedure workProcedure;

    @EruptField(
            views = {@View(title = "计划生产数量")},
            edit = @Edit(title = "计划生产数量", notNull = true, numberType = @NumberType(min = 0))
    )
    private BigDecimal num;

    @EruptField(
            views = {@View(title = "完成数量")},
            edit = @Edit(title = "完成数量", numberType = @NumberType(min = 0), show = false)
    )
    private BigDecimal finishNum;

    @EruptField(
            views = {@View(title = "废品数量")},
            edit = @Edit(title = "废品数量", numberType = @NumberType(min = 0), show = false)
    )
    private BigDecimal scrapNum;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "工单编号", column = "workOrder.orderCode"),
                    @View(title = "产品编号", column = "stock.code"),
                    @View(title = "产品名称", column = "stock.name")
            },
            edit = @Edit(title = "工单排产", readonly = @Readonly, show = false, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesProductionSchedule productionSchedule;

    @ManyToOne
    @EruptField(
            views = {@View(title = "上料清单", column = "version")},
            edit = @Edit(title = "上料清单", notNull = true, referenceTableType = @ReferenceTableType(label = "version"), type = EditType.REFERENCE_TABLE)
    )
    private MesFeedingList feedingList;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注")
    )
    private String remark;

    @EruptField(
            views = {@View(title = "计划开始时间")},
            edit = @Edit(title = "计划开始时间", notNull = true, dateType = @DateType(type = DateType.Type.DATE_TIME), search = @Search(vague = true))
    )
    private Date startDate;

    @EruptField(
            views = {@View(title = "计划结束时间")},
            edit = @Edit(title = "计划结束时间", notNull = true, dateType = @DateType(type = DateType.Type.DATE_TIME), search = @Search(vague = true))
    )
    private Date endDate;

    @EruptField(
            views = {@View(title = "实际开始时间")},
            edit = @Edit(title = "实际开始时间", readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME), search = @Search(vague = true), show = false)
    )
    private Date actualStartDate;

    @EruptField(
            views = {@View(title = "实际结束时间")},
            edit = @Edit(title = "实际结束时间", readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME), search = @Search(vague = true), show = false)
    )
    private Date actualEndDate;

    @EruptField(
            edit = @Edit(title = "租户", readonly = @Readonly, show = false)
    )



    private Boolean deleted = false;

    @OneToMany
    @JoinColumn(name = "task_id")
    // @JoinTable(name = "mes_production_schedule_user",
    //         joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
    //         inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @EruptField(
            views = @View(title = "员工姓名",column = "user.name"),
            edit = @Edit(title = "员工",
                    type = EditType.TAB_TABLE_ADD)
    )
    private List<MesProductionScheduleUser> userList;

    @EruptField
    private Long parentId;

    private Date createTime;
}
