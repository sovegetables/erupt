package com.qamslink.mes.model.sms;

import com.qamslink.mes.model.basic.MesGroupUser;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="mes_sms_user")
@Erupt(name = "用户")
@Getter
@Setter
public class MesSmsUser extends BaseModel {
    @ManyToOne
    @EruptField(
            views = {@View(title = "姓名", column = "name"),
                    @View(title = "手机号码", column = "phone")},
            edit = @Edit(title = "姓名", notNull = true, search = @Search(vague = true),type = EditType.REFERENCE_TABLE)

    )
    private MesGroupUser user;
}
