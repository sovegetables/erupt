package com.qamslink.mes.model.quality;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mes_inspection_mould")
@Getter
@Setter
@Erupt(name = "检验模板",
//        dataProxy = MesInspectionMouldService.class,
        orderBy = "MesInspectionMould.createTime desc",
        filter = @Filter(value = "MesInspectionMould.tenantId",
                params = {"and MesInspectionMould.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_inspection_mould set deleted = true where id = ?")
public class MesInspectionMould extends TenantCreatorModel {

    @EruptField(
            views = @View(title = "检验模板名称"),
            edit = @Edit(title = "检验模板名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "版本号"),
            edit = @Edit(title = "版本号", notNull = true)
    )
    private String version;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", notNull = true, search = @Search(vague = true),
                    boolType = @BoolType(trueText = "启动", falseText = "禁用")
            )
    )
    private Boolean status = true;

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mes_inspection_mould_detail",
            joinColumns = @JoinColumn(name = "inspection_mould_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "inspection_item_id", referencedColumnName = "id"))
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(
                    title = "详情",
                    type = EditType.TAB_TABLE_REFER
            )
    )
    private Set<MesInspectionItem> inspectionMould;*/

    @JoinColumn(name = "inspection_mould_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "检测项"),
            edit = @Edit(title = "检测项", type = EditType.TAB_TABLE_ADD)
//            ,
//            hasExtra = true
    )
    private List<MesInspectionItemMeasure> itemMeasures;

    @EruptField(
            views = @View(title = "租户", show = false, columnShowed = false)
    )
    private Long tenantId;

    private Boolean deleted = false;
}
