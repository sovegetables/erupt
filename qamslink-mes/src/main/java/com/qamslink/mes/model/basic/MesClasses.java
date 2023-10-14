package com.qamslink.mes.model.basic;

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

@Setter
@Getter
@Entity
@Table(name = "aps_classes")
@Erupt(name = "班次定义",
//        dataProxy = ApsClassesService.class,
        orderBy = "createTime desc",
        filter = @Filter(value = "MesClasses.tenantId",
                params = {"and MesClasses.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update aps_classes set deleted = true where id = ?")
public class MesClasses extends TenantCreatorModel {

    @EruptField(
            views = @View(title = "班次名称"),
            edit = @Edit(title = "班次名称", notNull = true, search = @Search())
    )
    private String name;

    @ManyToOne
    @EruptField(
            views = @View(title = "所属车间", column = "name"),
            edit = @Edit(title = "所属车间", type = EditType.REFERENCE_TABLE,
                    notNull = true,
                    search = @Search(vague = true))
    )
    private MesWorkshop workshop;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态",
                    boolType = @BoolType(trueText = "已启用", falseText = "已停用"))
    )
    private Boolean status = true;

    @Transient
    @EruptField(
            views = @View(title = "时间段")
    )
    private String workTimes;

    @Transient
    @EruptField(
            views = @View(title = "总时长")
    )
    private String totalTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "classes_id")
    @EruptField(
            edit = @Edit(title = "时间段", type = EditType.TAB_TABLE_ADD)
    )
    private List<MesClassesTime> classesTimes;

    private Long tenantId;
    private Boolean deleted = false;
}
