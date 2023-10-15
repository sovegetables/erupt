package com.qamslink.mes.model.maintenance;

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
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mes_maintenance_project")
@Getter
@Setter
@Erupt(name = "保养计划",
//        dataProxy = MesMaintenanceProjectService.class,
        orderBy = "MesMaintenanceProject.createTime desc",
        filter = @Filter(value = "MesMaintenanceProject.tenantId",
                params = {"and MesMaintenanceProject.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_maintenance_project set deleted = true where id = ?")
public class MesMaintenanceProject extends HyperModelVo {

    @EruptField(
            views = @View(title = "保养计划名称"),
            edit = @Edit(title = "保养计划名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "上次保养时间", type = ViewType.DATE_TIME)
    )
    private Date lastUseDate;

    @EruptField(
            views = @View(title = "下次保养时间",type = ViewType.DATE_TIME)
    )
    private Date nextUseDate;

    @EruptField(
            views = @View(title = "检验提示警报"),
            edit = @Edit(title = "检验提示警报", search = @Search, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "正常", value = "1"),
                            @VL(label = "检验警报", value = "2")
                    }
            ))
    )
    private Integer alarm;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", notNull = true, search = @Search, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "设备", value = "1"),
                            @VL(label = "工具", value = "2")
                    }
            ))
    )
    private Integer type;

    @ManyToOne
    @EruptField(
            views = @View(title = "保养模板", column = "name"),
            edit = @Edit(title = "保养模板", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesMaintenance maintenance;

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
            views = @View(title = "保养周期"),
            edit = @Edit(title = "保养周期", notNull = true, numberType = @NumberType(min = 1))
    )
    private BigDecimal periodicNum;

    @EruptField(
            views = @View(title = "周期单位"),
            edit = @Edit(title = "周期单位",
                    notNull = true,
                    type = EditType.CHOICE,
                    choiceType = @ChoiceType(
                            vl = {
                                    @VL(label = "日", value = "day"),
                                    @VL(label = "周", value = "week"),
                                    @VL(label = "月", value = "month"),
                                    @VL(label = "年", value = "year"),
                            }
                    ))
    )
    private String periodicUnit;

    @EruptField(
            views = @View(title = "租户", show = false, columnShowed = false)
    )
    private Long tenantId;

    private Boolean deleted = false;
}
