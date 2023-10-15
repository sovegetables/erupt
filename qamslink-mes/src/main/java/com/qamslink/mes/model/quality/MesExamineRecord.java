package com.qamslink.mes.model.quality;

import com.qamslink.mes.model.basic.MesWorkingProcedure;
import com.qamslink.mes.model.production.MesProductionScheduleDetail;
import com.qamslink.mes.model.production.MesWorkOrder;
import com.qamslink.mes.model.warehouse.MesWarehouse;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author LiYongGui
 * @create 2023-07-27 下午1:53
 */
@Setter
@Getter
@Entity
@Table(name = "mes_examine_record")
@Erupt(name = "检验出入记录",
//        dataProxy = MesExamineRecordService.class,
        orderBy = "MesExamineRecord.createTime desc",
        filter = @Filter(value = "MesExamineRecord.tenantId",
                params = {"and MesExamineRecord.deleted = false"},
                conditionHandler = TenantFilter.class
        )
)
@SQLDelete(sql = "update mes_examine_record set deleted = true where id = ?")
public class MesExamineRecord extends HyperModelVo {
    private static final long serialVersionUID = 2780907069821665641L;

    @EruptField(
            views = @View(title = "检验出入单号"),
            edit = @Edit(title = "检验出入单号", search = @Search(vague = true))
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "工单编号", column = "orderCode")
            },
            edit = @Edit(title = "工单编号", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "任务编号", column = "code")
            },
            edit = @Edit(title = "任务编号", type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesProductionScheduleDetail productionScheduleDetail;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "工序名称", column = "name")
            },
            edit = @Edit(title = "工序名称", type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesWorkingProcedure workingProcedure;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "接收员工姓名", column = "name"),
                    @View(title = "接收员工号", column = "empno")
            },
            edit = @Edit(title = "工号", type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private EruptUser eruptUser;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "发出员工姓名", column = "name"),
                    @View(title = "发出员工号", column = "empno")
            },
            edit = @Edit(title = "工号", type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private EruptUser outUser;

    @EruptField(
            views = {@View(title = "接收数量")},
            edit = @Edit(title = "接收数量", numberType = @NumberType(min = 0))
    )
    private BigDecimal obtainNum;

    @EruptField(
            views = {@View(title = "发出数量")},
            edit = @Edit(title = "发出数量", numberType = @NumberType(min = 0))
    )
    private BigDecimal sendNum;
    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "目的仓库", column = "name")
            },
            edit = @Edit(title = "目的仓库", type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesWarehouse warehouse;

    public static final int STATUS_IN = 1;
    public static final int STATUS_OUT = 2;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", readonly = @Readonly, type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "已接收", value = STATUS_IN + ""),
                            @VL(label = "已发出", value = STATUS_OUT + "")
                    }))
    )
    private Integer status;

    @EruptField(
            views = {@View(title = "备注")},
            edit = @Edit(title = "备注")
    )
    private String remark;
    private Boolean deleted = false;
}
