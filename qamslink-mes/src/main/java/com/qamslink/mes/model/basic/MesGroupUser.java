package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "e_upms_user")
@Setter
@Getter
@Erupt(name = "用户配置",
//        dataProxy = MesGroupUserService.class,
        filter = @Filter(
                value = "MesGroupUser.tenantId",
                conditionHandler = TenantFilter.class
        )
)
public class MesGroupUser extends HyperModelVo {

    @EruptField(
            views = @View(title = "姓名"),
            edit = @Edit(title = "姓名", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "手机号码"),
            edit = @Edit(title = "手机号码", notNull = true, search = @Search(vague = true))
    )
    private String phone;


}
