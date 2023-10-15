package com.qamslink.mes.model.sms;

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
@Table(name = "e_upms_role")
@Setter
@Getter
@Erupt(name = "角色",
//        dataProxy = MesSmsRoleService.class,
        filter = @Filter(
                value = "MesSmsRole.tenantId",
                conditionHandler = TenantFilter.class
        )
)
public class MesSmsRole extends HyperModelVo {

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
}
