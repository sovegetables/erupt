package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.EruptI18n;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.BoolType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.handler.RoleMenuFilter;
import xyz.erupt.upms.model.EruptMenu;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "mes_default_role")
@Erupt(name = "默认角色", power = @Power(add = false))
@EruptI18n
@Getter
@Setter
@Component
public class MesDefaultRole extends BaseModel {
    public static final String CODE_ENTERPRISE_MANAGER = "enterprise_manager";
    public static final String CODE_PURCHARSE_MANAGER = "purchase_manager";
    public static final String CODE_PURCHARSE_MEMBER = "purchase_member";
    public static final String CODE_MO_PLAN_MEMBER = "plan_member";
    public static final String CODE_MO_MEMBER = "code_mo_member";
    public static final String CODE_MO_MANAGER = "code_mo_manager";
    public static final String CODE_WAREHOURSE_MANAGER = "warehourse_manager";
    public static final String CODE_WAREHOURSE_MEMBER = "warehourse_member";
    public static final String CODE_EQUIMENT_MANAGER = "equiment_manager";
    public static final String CODE_EQUIMENT_MEMBER = "equiment_memeber";
    public static final String CODE_INSPECTION_MANAGER = "inspection_manager";
    public static final String CODE_INSPECTION_MEMBER = "inspection_member";
    public static final String CODE_MODULE_MANAGER = "moule_manager";
    public static final String CODE_SUPPLIER_MANAGER = "supplier_manager";

    @EruptField(
            views = @View(title = "编码"),
            edit = @Edit(title = "编码", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @EruptField(
            views = @View(title = "名称"),
            edit = @Edit(title = "名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "状态", sortable = true),
            edit = @Edit(
                    title = "状态",
                    type = EditType.BOOLEAN,
                    notNull = true,
                    search = @Search(vague = true),
                    boolType = @BoolType(trueText = "启用", falseText = "禁用")
            )
    )
    private Boolean status = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mes_default_role_menu",
            joinColumns = @JoinColumn(name = "default_role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    @EruptField(
            views = @View(title = "菜单权限"),
            edit = @Edit(
                    filter = @Filter(conditionHandler = RoleMenuFilter.class),
                    title = "菜单权限",
                    type = EditType.TAB_TREE
            )
    )
    private Set<EruptMenu> menus;
}
