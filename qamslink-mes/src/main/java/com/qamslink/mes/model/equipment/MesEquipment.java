package com.qamslink.mes.model.equipment;

import com.qamslink.mes.model.basic.MesCustomer;
import com.qamslink.mes.model.tool.MesMouldBom;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "mes_equipment")
@Erupt(name = "设备列表",
        orderBy = "MesEquipment.createTime desc",
        power = @Power(importable = true,export = true),
        linkTree = @LinkTree(fieldClass = "MesEquipmentCategory", field = "equipmentCategory"),
//        dataProxy = MesEquipmentService.class,
        filter = @Filter(value = "MesEquipment.tenantId", conditionHandler = TenantFilter.class))
public class MesEquipment extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "设备名称"),
            edit = @Edit(title = "设备名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "机台编号"),
            edit = @Edit(title = "机台编号", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @EruptField(
            views = @View(title = "设备编号"),
            edit = @Edit(title = "设备编号", notNull = true, search = @Search(vague = true))
    )
    private String equipmentCode;

    @ManyToOne
    @EruptField(
            views = @View(title = "所属设备分类", column = "name"),
            edit = @Edit(title = "所属设备分类", notNull = true, type = EditType.REFERENCE_TABLE)
    )
    private MesEquipmentCategory equipmentCategory;

    @EruptField(
            views = @View(title = "设备型号"),
            edit = @Edit(title = "设备型号")
    )
    private String version;

    @EruptField(
            views = @View(title = "固定资产编码"),
            edit = @Edit(title = "固定资产编码")
    )
    private String fixedAssetCode;

    @ManyToOne
    @EruptField(
            views = @View(title = "供应商", column = "name"),
            edit = @Edit(title = "供应商", type = EditType.REFERENCE_TABLE)
    )
    private MesCustomer supplier;

    @EruptField(
            views = @View(title = "存放位置"),
            edit = @Edit(title = "存放位置")
    )
    private String address;

    @EruptField(
            views = @View(title = "入厂日期"),
            edit = @Edit(title = "入厂日期", type = EditType.DATE)
    )
    private Date incomingDate;

    @EruptField(
            views = @View(title = "保修期（天）"),
            edit = @Edit(title = "保修期（天）")
    )
    private Integer warranty;

    @EruptField(
            views = @View(title = "设备状态"),
            edit = @Edit(title = "设备状态", type = EditType.CHOICE,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "正常", value = "1"),
                            @VL(label = "故障", value = "2"),
                            @VL(label = "报废", value = "3")
                    }))
    )
    private Integer status = 1;

    @ManyToMany
    @JoinTable(
            name = "mes_equipment_mould_bom",
            joinColumns = @JoinColumn(name = "equiment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "mould_bom_id", referencedColumnName = "id"))
    @EruptField(
            views = @View(title = "关联工具BOM", column = "name"),
            edit = @Edit(title = "关联工具BOM", type = EditType.TAB_TABLE_REFER)
    )
    private Set<MesMouldBom> mouldBoms;

    @EruptField
    private Long tenantId;

    private Boolean deleted = false;
}
