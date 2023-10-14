package com.qamslink.mes.model.tool;

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

@Getter
@Setter
@Entity
@Table(name = "mes_tool_category")
@Erupt(name = "工具分类",
//        dataProxy = MesToolCategoryService.class,
        filter = @Filter(value = "MesToolCategory.tenantId",
                params = {"and MesToolCategory.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_tool_category set deleted = true where id = ?")
public class MesToolCategory extends HyperModelCreatorVo {

    @EruptField(
            views = @View(title = "分类名称"),
            edit = @Edit(title = "分类名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "分类编码"),
            edit = @Edit(title = "分类编码", notNull = true, search = @Search(vague = true))
    )
    private String code;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @EruptField(
            views = @View(title = "租户", show = false, columnShowed = false)
    )
    private Long tenantId;

    private Boolean deleted = false;
}
