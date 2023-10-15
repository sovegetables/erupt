package com.qamslink.mes.model.production;

import com.qamslink.mes.model.basic.MesWorkingProcedure;
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
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mes_work_start")
@Setter
@Getter
@Erupt(name = "开工记录",
//        dataProxy = MesWorkStartService.class,
        orderBy = "MesWorkStart.createTime desc",
        filter = @Filter(
                value = "MesWorkStart.tenantId",
                params = {"and MesWorkStart.deleted = false"},
                conditionHandler = TenantFilter.class
        ),
        power = @Power(add = false, edit = false, delete = false)

)
@SQLDelete(sql = "update mes_work_start set deleted = true where id = ?")
public class MesWorkStart extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views = {@View(title = "生产工单号", column = "orderCode"),
                    @View(title = "物料名称", column = "stock.name"),
                    @View(title = "物料编码", column = "stock.code")},
            edit = @Edit(title = "生产工单号", notNull = true,
                    search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @EruptField(
            views = @View(title = "流转卡号")
    )
    @Transient
    private String flowCardCode;

    @ManyToOne
    @EruptField(
            views = {@View(title = "任务编号", column = "code")},
            edit = @Edit(title = "任务编号", notNull = true, search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesProductionScheduleDetail task;

    @OneToOne
    @EruptField(
            views = {@View(title = "开工员工", column = "account"),
                    @View(title = "开工员工号", column = "empno")},
            edit = @Edit(title = "开工员工", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private EruptUser user;

    @OneToOne
    @EruptField(
            views = {@View(title = "报工员工", column = "account"),
                    @View(title = "报工员工号", column = "empno")},
            edit = @Edit(title = "报工员工", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private EruptUser endUser;


    @OneToOne
    @EruptField(
            views = {@View(title = "工序名称", column = "name")},
            edit = @Edit(title = "工序名称", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesWorkingProcedure workingProcedure;

    @EruptField(
        views = @View(title = "报工数量")
    )
    private BigDecimal finishCount;

    @EruptField(
            views = @View(title = "状态", sortable = true),
            edit = @Edit(
                    title = "状态",
                    type = EditType.BOOLEAN,
                    notNull = true,
                    search = @Search(vague = true),
                    boolType = @BoolType(trueText = "未报工", falseText = "已报工")
            )
    )
    private Boolean status = true;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @EruptField(
            views = {@View(title = "开工日期")},
            edit = @Edit(title = "开工日期", notNull = true, editShow = false , readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date startTime;

    @EruptField(
            views = {@View(title = "完工日期")},
            edit = @Edit(title = "完工日期", notNull = true, editShow = false , readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date finishTime;

    @EruptField(
            edit = @Edit(title = "租户", readonly = @Readonly, show = false)
    )
    private Long tenantId;

    private String barcodeNum;

    private Boolean deleted = false;
}
