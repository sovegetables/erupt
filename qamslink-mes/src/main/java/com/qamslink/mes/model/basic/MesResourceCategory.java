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
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "mes_resource_category")
@Erupt(name = "资源类型",
//        dataProxy = MesResourceCategoryService.class,
        filter = @Filter(value = "MesResourceCategory.tenantId",
                params = {"and MesResourceCategory.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_resource_category set deleted = true where id = ?")
public class MesResourceCategory extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "资源类型名称"),
            edit = @Edit(title = "资源类型名称", search = @Search(vague = true), notNull = true)
    )
    private String name;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述", type = EditType.TEXTAREA)
    )
    private String description;

    @EruptField
    private Long tenantId;

    private Boolean deleted = false;
}
