package com.qamslink.mes.model.sms;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ChoiceType;
import xyz.erupt.annotation.sub_field.sub_edit.InputType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.annotation.sub_field.sub_edit.VL;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "mes_sms_template")
@Getter
@Setter
@Erupt(name = "消息模板",
//        dataProxy = MesSmsTemplateService.class,
        orderBy = "MesSmsTemplate.createTime desc",
        filter = @Filter(value = "MesSmsTemplate.tenantId",
                params = {"and MesSmsTemplate.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_sms_template set deleted = true where id = ?")
public class MesSmsTemplate extends TenantCreatorModel {

    @EruptField(
            views = @View(
                    title = "模板名称"
            ),
            edit = @Edit(
                    title = "模板名称",
                    type = EditType.INPUT, search = @Search(vague = true), notNull = true,
                    inputType = @InputType
            )
    )
    private String name;

    @EruptField(
            views = @View(
                    title = "模板code"
            )
    )
    private String code;

    @ManyToMany
    @JoinTable(name = "mes_sms_role",
            joinColumns = @JoinColumn(name = "sms_template_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @EruptField(
            views = @View(title = "消息推送对象"),
            edit = @Edit(
                    title = "消息推送对象",
                    type = EditType.TAB_TABLE_REFER
            )
    )
    private Set<MesSmsRole> smsRoles;

    @EruptField(
            views = @View(
                    title = "信息来源"
            ),
            edit = @Edit(
                    title = "信息来源",
                    type = EditType.INPUT, notNull = true,
                    inputType = @InputType
            )
    )
    private String smsSource;

    @EruptField(
            views = @View(title = "推送周期"),
            edit = @Edit(title = "推送周期", type = EditType.CHOICE, notNull = true, search = @Search,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "日",value = "1"),
                            @VL(label = "周", value = "2"),
                            @VL(label = "月", value = "3")
                    }))
    )
    private Integer pushCycle;

    @EruptField(
            views = @View(
                    title = "推送内容"
            ),
            edit = @Edit(
                    title = "推送内容",
                    type = EditType.TEXTAREA,
                    notNull = true
            )
    )
    private String pushContent;
    @EruptField
    private String remark;
    @EruptField(views = @View(title = "是否删除", show = false, columnShowed = false))
    private Boolean deleted = false;
}
