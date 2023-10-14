package com.qamslink.mes.model.machine;

import com.qamslink.mes.model.basic.MesWorkingProcedure;
import com.qamslink.mes.model.equipment.MesEquipment;
import com.qamslink.mes.model.production.MesProductionScheduleDetail;
import com.qamslink.mes.model.production.MesWorkOrder;
import com.qamslink.mes.model.upload.MesUploadImage;
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
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.TenantCreatorModel;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author LiYongGui
 * @create 2023-07-25 下午2:50
 */

@Setter
@Getter
@Entity
@Table(name = "mes_set_machine")
@Erupt(name = "调机记录",
//        dataProxy = MesSetMachineService.class,
        power = @Power(add = false, edit = false, delete = false),
        orderBy = "MesSetMachine.createTime desc",
        filter = @Filter(value = "MesSetMachine.tenantId",
                params = {"and MesSetMachine.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_set_machine set deleted = true where id = ?")
public class MesSetMachine extends TenantCreatorModel {

    private static final long serialVersionUID = -5811398935637123655L;

    @EruptField(
        views = @View(title = "调机单号"),
        edit = @Edit(title = "调机单号", search = @Search(vague = true))
    )
    private String code;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "工序名称", column = "name")
            },
            edit = @Edit(title = "工序名称", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesWorkingProcedure workingProcedure;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "调机人员名字", column = "name"),
                    @View(title = "调机人员工号", column = "empno")
            },
            edit = @Edit(title = "工号", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private EruptUser eruptUser;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "设备名称", column = "name"),
                    @View(title = "设备编号", column = "code")
            },
            edit = @Edit(title = "设备", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesEquipment equipment;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", readonly = @Readonly, type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "调机中", value = "1"),
                            @VL(label = "调机完成", value = "2")
                    }))
    )
    private Integer status;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "工单编号", column = "orderCode")
            },
            edit = @Edit(title = "工单编号", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "orderCode"))
    )
    private MesWorkOrder workOrder;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "任务编号", column = "code")
            },
            edit = @Edit(title = "任务编号", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true),
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesProductionScheduleDetail productionScheduleDetail;

    @EruptField(
            views = @View(title = "开始时间"),
            edit = @Edit(title = "开始时间", dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date startDate;

    @EruptField(
            views = @View(title = "结束时间"),
            edit = @Edit(title = "结束时间", dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date endDate;

    @EruptField(
            views = @View(title = "调机类型", show = true),
            edit = @Edit(title = "调机类型", readonly = @Readonly, type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "首次调机", value = "1"),
                            @VL(label = "重复调机", value = "2")
                    }))
    )
    private Integer type;

    @EruptField(
            views = {@View(title = "备注")},
            edit = @Edit(title = "备注")
    )
    private String remark;

    @ManyToMany
    @JoinTable(name = "mes_set_machine_upload_image",
            joinColumns = @JoinColumn(name = "set_machine_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "upload_image_id", referencedColumnName = "id"))
    @EruptField(
            views = @View(title = "图片详情"),
            edit = @Edit(title = "图片详情", type = EditType.TAB_TABLE_ADD
            )
    )
    private List<MesUploadImage> uploadImages;

    private Boolean deleted = false;
}
