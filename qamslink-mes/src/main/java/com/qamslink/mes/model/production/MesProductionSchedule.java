package com.qamslink.mes.model.production;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qamslink.mes.model.basic.MesStock;
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
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "is_down", discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "mes_production_schedule")
@Setter
@Getter
@Erupt(name = "排产工单",
//        dataProxy = MesProductionScheduleService.class,
        orderBy = "MesProductionSchedule.createTime desc",
        filter = @Filter(value = "MesProductionSchedule.tenantId",
                params = {"and MesProductionSchedule.deleted = false"},
                conditionHandler = TenantFilter.class),
        power = @Power(add = false, delete = false, edit = false),
        rowOperation = {
                @RowOperation(
                        code = "termination",
                        title = "终止",
                        mode = RowOperation.Mode.MULTI
//                        ,
//                        operationHandler = ProductionScheduleHandler.class
                )
        }
)
@SQLDelete(sql = "update mes_production_schedule set deleted = true where id = ?")
public class MesProductionSchedule extends HyperModelVo {

    private static final long serialVersionUID = -704535462879694811L;

    public static final int STATU_UN_START = 0;
    public static final int STATU_STARTED = 1;
    public static final int STATU_FINISH_START = 2;
    public static final int STATU_FINISH = 3;

    @EruptField(
            views = {@View(title = "排产号")},
            edit = @Edit(title = "排产号", notNull = true, readonly = @Readonly())
    )
    private String code;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", show = false, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "未排产", value = STATU_UN_START + ""),
                            @VL(label = "部分排产", value = STATU_STARTED + ""),
                            @VL(label = "已排产", value = STATU_FINISH_START + ""),
                            @VL(label = "已完成", value = STATU_FINISH + "")
                    }
            ))
    )
    private Integer status;

    @ManyToOne
    @EruptField(
            views = {@View(title = "工序名称", column = "name")},
            edit = @Edit(title = "工序名称", notNull = true, readonly = @Readonly(),
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "name"), search = @Search(vague = true))
    )
    private MesWorkingProcedure workingProcedure;

    @ManyToOne
    @EruptField(
            views = {@View(title = "生产工单", column = "orderCode")},
            edit = @Edit(title = "生产工单", notNull = true, search = @Search(vague = true),
                    readonly = @Readonly(),
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "普通", value = "1"),
                            @VL(label = "返工", value = "2")
                    }
            ))
    )
    private Integer type = 1;

    @EruptField(
            views = @View(title = "完成状态"),
            edit = @Edit(title = "完成状态", readonly = @Readonly(), type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "普通", value = "1"),
                            @VL(label = "终止", value = "2")
                    }
            ))
    )
    private Integer finishStatus = 1;

    @ManyToOne
    @EruptField(
            views = {@View(title = "产品编码", column = "code"),
                    @View(title = "产品名称", column = "name")},
            edit = @Edit(title = "产品名称", notNull = true,
                    readonly = @Readonly(),
                    type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "数量"),
            edit = @Edit(title = "数量", readonly = @Readonly())
    )
    private BigDecimal capacity;

    @EruptField(
            views = @View(title = "可排产数量"),
            edit = @Edit(title = "可排产数量", readonly = @Readonly())
    )
    private BigDecimal scheduledNum;

    @EruptField(
            views = @View(title = "已排产数量"),
            edit = @Edit(title = "已排产数量", readonly = @Readonly())
    )
    private BigDecimal schedleNum;

    @EruptField(
            views = @View(title = "完成数量"),
            edit = @Edit(title = "完成数量", readonly = @Readonly())
    )
    private BigDecimal quantityCompletion;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注")
    )
    private String remark;

    @EruptField(
            views = @View(title = "开始日期"),
            edit = @Edit(title = "开始日期", readonly = @Readonly())
    )
    private Date startDate;

    @EruptField(
            views = @View(title = "结束日期"),
            edit = @Edit(title = "结束日期", readonly = @Readonly())
    )
    private Date endDate;

    @EruptField(
            views = @View(title = "首检"),
            edit = @Edit(title = "首检", notNull = true, boolType = @BoolType(trueText = "是", falseText = "否"))
    )
    private Boolean firstInspection = false;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "production_schedule_id")
    @EruptField(
            views = @View(title = "排产详情"),
            edit = @Edit(title = "排产", type = EditType.TAB_TABLE_ADD)
    )
    @JsonIgnore
    private List<MesProductionScheduleDetail> details;



    private Boolean deleted = false;
    @Transient
    private Long procedureId;
}
