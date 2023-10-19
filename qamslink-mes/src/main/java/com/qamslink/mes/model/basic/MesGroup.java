package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "mes_group")
@Setter
@Getter
@Erupt(name = "班组管理",
//        dataProxy = MesGroupService.class,
        orderBy = "MesGroup.createTime desc")
public class MesGroup extends HyperModelVo {

    @EruptField(
            views = @View(title = "班组编码", highlight = true),
            edit = @Edit(title = "班组编码", placeHolder = "保存时自动生成",
                    notNull = true,
                    search = @Search(vague = true))
    )
    @CodeGenerator
    private String code;

    @EruptField(
            views = @View(title = "班组名称"),
            edit = @Edit(title = "班组名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @ManyToOne
    @EruptField(
            views = @View(title = "班组负责人", column = "name"),
            edit = @Edit(title = "班组负责人", type = EditType.REFERENCE_TABLE, notNull = true, search = @Search(vague = true))
    )
    private EruptUser manager;

    @ManyToOne
    @EruptField(
            views = @View(title = "所属车间", column = "name"),
            edit = @Edit(title = "所属车间", type = EditType.REFERENCE_TABLE,
                    notNull = true,
                    search = @Search(vague = true))
    )
    private MesWorkshop workshop;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @ManyToMany
    @JoinTable(name = "mes_group_user",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @EruptField(
            views = @View(title = "班组人员"),
            edit = @Edit(
                    title = "班组人员",
                    type = EditType.TAB_TABLE_REFER
            )
    )
    private Set<MesGroupUser> users;
    private Boolean deleted = false;
}
