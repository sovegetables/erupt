package com.qamslink.mes.model.tool;

import com.qamslink.mes.model.equipment.MesEquipment;
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
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_equipment_mould")
@Setter
@Getter
@Erupt(name = "工具上下模记录",
//        dataProxy = MesEquipmentMouldService.class,
        orderBy = "MesEquipmentMould.createTime desc",
        filter = @Filter(value = "MesEquipmentMould.tenantId",
                params = {"and MesEquipmentMould.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_equipment_mould set deleted = true where id = ?")
public class MesEquipmentMould extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views = {@View(title = "设备名称", column = "name"),
                    @View(title = "设备编码", column = "code")},
            edit = @Edit(title = "设备名称", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesEquipment equipment;

    @ManyToOne
    @EruptField(
            views = {@View(title = "工具名称", column = "name"),
                    @View(title = "工具编码", column = "code")},
            edit = @Edit(title = "工具", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesMould mould;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", notNull = true, search = @Search, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "下模", value = "0"),
                            @VL(label = "上模", value = "1")
                    }
            ))
    )
    private Integer status;
    private Integer mark;
    private Boolean deleted = false;
}
