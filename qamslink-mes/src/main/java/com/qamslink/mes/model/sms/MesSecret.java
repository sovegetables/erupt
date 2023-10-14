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
import xyz.erupt.upms.model.EruptOrg;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_secret")
@Getter
@Setter
@Erupt(name = "密钥管理",
//        dataProxy = MesSecretService.class,
        orderBy = "MesSecret.createTime desc",
        filter = @Filter(value = "MesSecret.tenantId",
                params = {"and MesSecret.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_secret set deleted = true where id = ?")
public class MesSecret extends HyperModelCreatorVo {

    @ManyToOne
    @EruptField(
            views = @View(title = "所属组织", column = "name"),
            edit = @Edit(title = "所属组织", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private EruptOrg EruptOrg;

    @EruptField(
            views = @View(title = "类型"),
            edit = @Edit(title = "类型", type = EditType.CHOICE, notNull = true, search = @Search,
                    choiceType = @ChoiceType(vl = {
                            @VL(label = "企业微信",value = "1"),
                            @VL(label = "钉钉", value = "2")
                    }))
    )
    private Integer secretType;

    @EruptField(
            views = @View(
                    title = "企业ID（企业微信/钉钉）"
            ),
            edit = @Edit(
                    title = "企业ID（企业微信/钉钉）",
                    type = EditType.INPUT, notNull = true,
                    inputType = @InputType
            )
    )
    private String corpid;

    @EruptField(
            views = @View(
                    title = "密钥"
            ),
            edit = @Edit(
                    title = "密钥",
                    type = EditType.TEXTAREA, notNull = true,
                    inputType = @InputType
            )
    )
    private String corpsecret;

    @EruptField
    private Long tenantId;

//    @EruptField(
//            views = @View(
//                    title = "备注"
//            ),
//            edit = @Edit(
//                    title = "备注",
//                    type = EditType.TEXTAREA
//            )
//    )
    @EruptField
    private String remark;

    @EruptField(views = @View(title = "是否删除", show = false, columnShowed = false))
    private Boolean deleted = false;
}
