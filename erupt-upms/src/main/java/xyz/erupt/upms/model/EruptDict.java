package xyz.erupt.upms.model;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.EruptI18n;
import xyz.erupt.annotation.constant.AnnotationConst;
import xyz.erupt.annotation.sub_erupt.Drill;
import xyz.erupt.annotation.sub_erupt.Link;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author YuePeng
 * date 2018-12-07.
 */

@Entity
@Table(name = "e_dict", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@EruptI18n
@Erupt(
        name = "数据字典",
        power = @Power(),
        orderBy = "EruptDict.updateTime desc",
        drills = @Drill(
                title = "字典项",
                link = @Link(
                        linkErupt = EruptDictItem.class, joinColumn = "eruptDict.id"
                )
        )
)
@Getter
@Setter
public class EruptDict extends HyperModel {

    @Column(length = AnnotationConst.CODE_LENGTH)
    @EruptField(
            views = @View(title = "编码", sortable = true, highlight = true),
            edit = @Edit(title = "编码", placeHolder = "保存时自动生成", search = @Search(vague = true), notNull = true)
    )
    @CodeGenerator
    private String code;

    @EruptField(
            views = @View(title = "名称", sortable = true),
            edit = @Edit(title = "名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

}
