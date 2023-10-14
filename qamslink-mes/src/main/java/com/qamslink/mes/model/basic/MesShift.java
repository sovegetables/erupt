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
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mes_shift")
@Setter
@Getter
@Erupt(name = "生产班次",
//        dataProxy = MesShiftService.class,
        filter = @Filter(value = "MesShift.tenantId",
                params = {"and MesShift.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_shift set deleted = true where id = ?")
public class MesShift extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "班次名称"),
            edit = @Edit(title = "班次名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "班次说明"),
            edit = @Edit(title = "班次说明")
    )
    private String description;

    @ManyToOne
    @EruptField(
            views = @View(title = "生产班次负责人", column = "name"),
            edit = @Edit(title = "生产班次负责人", type = EditType.REFERENCE_TABLE, notNull = true, search = @Search(vague = true))
    )
    private EruptUser user;

    @EruptField(
            views = @View(title = "班次开始时间"),
            edit = @Edit(title = "班次开始时间", notNull = true, search = @Search(vague = true), dateType = @DateType(type = DateType.Type.DATE))
    )
    private Date startDate;

    @EruptField(
            views = @View(title = "班次结束时间"),
            edit = @Edit(title = "班次结束时间", notNull = true, search = @Search(vague = true), dateType = @DateType(type = DateType.Type.DATE))
    )
    private Date endDate;

    @EruptField(
            views = @View(title = "上班时间"),
            edit = @Edit(title = "上班时间", notNull = true, search = @Search(vague = true), type = EditType.DATE, dateType = @DateType(type = DateType.Type.TIME))
    )
    private String workTime;

    @EruptField(
            views = @View(title = "下班时间"),
            edit = @Edit(title = "下班时间", notNull = true, search = @Search(vague = true), type = EditType.DATE, dateType = @DateType(type = DateType.Type.TIME))

    )
    private String returnTime;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注")
    )
    private String remark;



    @JoinTable(name = "mes_shift_group",
            joinColumns = @JoinColumn(name = "shift_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    @ManyToMany
    @EruptField(
            views = @View(title = "班组信息"),
            edit = @Edit(
                    title = "班组信息",
                    type = EditType.TAB_TABLE_REFER,
                    filter = @Filter(value = "MesShiftGroup.tenantId", conditionHandler = TenantFilter.class)
            )
    )
    private Set<MesShiftGroup> users;
    private Boolean deleted = false;
}
