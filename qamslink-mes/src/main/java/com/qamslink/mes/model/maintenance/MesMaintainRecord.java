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
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.ShowBy;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "mes_maintain_record")
@Erupt(name = "保养记录",
//        dataProxy = MesMaintainRecordService.class,
        orderBy = "MesMaintainRecord.createTime desc",
        filter = @Filter(value = "MesMaintainRecord.tenantId",
                params = {"and MesMaintainRecord.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_maintain_record set deleted = true where id = ?")
public class MesMaintainRecord extends TenantCreatorModel {

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

    @ManyToOne
    @EruptField(
            views = @View(title = "保养计划", column = "name"),
            edit = @Edit(title = "保养计划", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesMaintenanceProject maintenanceProject;

    @ManyToOne
    @EruptField(
            views = @View(title = "保养模板", column = "name"),
            edit = @Edit(title = "保养模板", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesMaintenance maintenance;

    @ManyToOne
    @EruptField(
            views = @View(title = "保养人员", column = "name"),
            edit = @Edit(title = "保养人员", type = EditType.REFERENCE_TABLE)
    )
    private MesGroupUser user;

    @JoinColumn(name = "maintain_record_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(title = "详情", type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesMaintainRecordDetail> maintainRecordDetails;
    private Boolean deleted = false;

}
