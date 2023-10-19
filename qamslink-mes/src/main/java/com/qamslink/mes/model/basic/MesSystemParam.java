package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.EruptI18n;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mes_system_param")
@Erupt(
        name = "参数配置"
//        dataProxy = MesSystemParamService.class,
)
@EruptI18n
@Getter
@Setter
@NoArgsConstructor
public class MesSystemParam extends HyperModel {

    public static final String PARAM_CODE_FLOW_CARD = "flowCard";

    @EruptField(
            views = @View(title = "参数编码", sortable = true),
            edit = @Edit(title = "参数编码", placeHolder = "保存时自动生成",
                    search = @Search(vague = true))
    )
    private String code;


    @EruptField(
            views = @View(title = "参数名称"),
            edit = @Edit(title = "参数名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "参数值"),
            edit = @Edit(title = "参数值", notNull = true)
    )
    private Integer paramValue;

    @EruptField(
            views = @View(title = "参数说明"),
            edit = @Edit(title = "参数说明", notNull = true)
    )
    private String remark;
}
