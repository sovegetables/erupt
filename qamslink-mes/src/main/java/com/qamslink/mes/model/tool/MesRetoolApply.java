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
@Table(name = "mes_retool_apply")
@Setter
@Getter
@Erupt(name = "工具更换申请",
//        dataProxy = MesRetoolApplyService.class,
        orderBy = "MesRetoolApply.createTime desc",
        filter = @Filter(value = "MesRetoolApply.tenantId",
                params = {"and MesRetoolApply.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_retool_apply set deleted = true where id = ?")
public class MesRetoolApply extends HyperModelVo {

    @EruptField(
            views = @View(title = "申请单号"),
            edit = @Edit(title = "申请单号", notNull = true, search = @Search(vague = true))
    )
    private String sn;

    @ManyToOne
    @EruptField(
            views = {@View(title = "设备名称", column = "name"),
                    @View(title = "设备编码", column = "code")},
            edit = @Edit(title = "设备名称", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesEquipment equipment;

    @ManyToOne
    @EruptField(
            views = {@View(title = "更换后工具名称", column = "name"),
                    @View(title = "更换后工具编码", column = "code")},
            edit = @Edit(title = "更换前工具", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesMould currentMould;

    @ManyToOne
    @EruptField(
            views = {@View(title = "更换后工具名称", column = "name"),
                    @View(title = "更换后工具编码", column = "code")},
            edit = @Edit(title = "更换后工具", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesMould newMould;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", notNull = true, search = @Search, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "未审核", value = "0"),
                            @VL(label = "已审核", value = "1"),
                            @VL(label = "作废", value = "2")
                    }
            ))
    )
    private Integer status;
    private Boolean deleted = false;
}
