package com.qamslink.mes.model.maintenance;

import com.qamslink.mes.model.basic.MesGroupUser;
import com.qamslink.mes.model.equipment.MesEquipment;
import com.qamslink.mes.model.tool.MesMould;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mes_repair_record")
@Getter
@Setter
@Erupt(name = "维修工单",
//        dataProxy = MesRepairRecordService.class,
        orderBy = "MesRepairRecord.createTime desc",
        filter = @Filter(value = "MesRepairRecord.tenantId",
                params = {"and MesRepairRecord.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_repair_record set deleted = true where id = ?")
public class MesRepairRecord extends HyperModelVo {

    @EruptField(
            views = @View(title = "维修单号"),
            edit = @Edit(title = "维修单号", notNull = true, search = @Search(vague = true))
    )
    private String repairSn;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", type = EditType.CHOICE, notNull = true, search = @Search,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "设备", value = "1"),
                            @VL(label = "工具", value = "2")
                    }))
    )
    private Integer type;

    @ManyToOne
    @EruptField(
            views = {@View(title = "设备名称", column = "name"),
                    @View(title = "设备编码", column = "code")},
            edit = @Edit(title = "设备名称", search = @Search(vague = true), type = EditType.REFERENCE_TABLE,
                    showBy = @ShowBy(dependField = "type", expr = "value == 1"))
    )
    private MesEquipment equipment;

    @ManyToOne
    @EruptField(
            views = {@View(title = "工具名称", column = "name"),
                    @View(title = "工具编码", column = "code")},
            edit = @Edit(title = "工具名称", search = @Search(vague = true), type = EditType.REFERENCE_TABLE,
                    showBy = @ShowBy(dependField = "type", expr = "value == 2"))
    )
    private MesMould mould;

    @EruptField(
            views = @View(title = "维修时长（分钟）"),
            edit = @Edit(title = "维修时长（分钟）", numberType = @NumberType(min = 0))
    )
    private BigDecimal duration;

    @EruptField(
            views = @View(title = "送修时间"),
            edit = @Edit(title = "送修时间", search = @Search(vague = true))
    )
    private Date repairTime;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述", type = EditType.TEXTAREA)
    )
    private String description;

    @EruptField(
            views = @View(title = "解决方案"),
            edit = @Edit(title = "解决方案", type = EditType.TEXTAREA)
    )
    private String solution;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", show = false, type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "待维修", value = "0"),
                            @VL(label = "维修中", value = "1"),
                            @VL(label = "已维修", value = "2"),
                            @VL(label = "作废", value = "3")
                    }))
    )
    private Integer status = 0;

    @ManyToOne
    @EruptField(
            views = @View(title = "维修人员", column = "name"),
            edit = @Edit(title = "维修人员", type = EditType.REFERENCE_TABLE)
    )
    private MesGroupUser user;
    private Boolean deleted = false;
}
