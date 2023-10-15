package com.qamslink.mes.model.sms;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "mes_sms")
@Getter
@Setter
@Erupt(name = "消息列表",
//        dataProxy = MesSmsService.class,
        orderBy = "MesSms.createTime desc",
        filter = @Filter(value = "MesSms.tenantId",
                params = {"and MesSms.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_sms set deleted = true where id = ?")
public class MesSms extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views = @View(title = "消息模板", column = "name"),
            edit = @Edit(title = "消息模板", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesSmsTemplate template;

    @EruptField(
            views = @View(title = "模板CODE")
    )
    private String templateCode;

    @EruptField(
            views = @View(title = "推送内容")
    )
    private String pushContent;

    @JoinColumn(name = "sms_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "推送对象"),
            edit = @Edit(title = "推送对象", readonly = @Readonly, type = EditType.TAB_TABLE_ADD)
    )
    private Set<MesSmsUser> users;

    @EruptField
    private Integer pageView;

    @EruptField
    private Integer likes;

    @EruptField
    private String smsSource;

    @EruptField
    private Boolean isRead;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;
    @EruptField(views = @View(title = "是否删除", show = false, columnShowed = false))
    private Boolean deleted = false;
}
