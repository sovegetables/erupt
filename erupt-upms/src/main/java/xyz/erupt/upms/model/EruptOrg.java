package xyz.erupt.upms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.EruptI18n;
import xyz.erupt.annotation.constant.AnnotationConst;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_erupt.Tree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTreeType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.core.annotation.CodeGenerator;

import javax.persistence.*;

/**
 * @author YuePeng
 * date 2020-04-08
 */
@Entity
@Table(name = "e_upms_org", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@Erupt(
        name = "组织维护",
        linkTree = @LinkTree(field = "parentOrg"),
        tree = @Tree(pid = "id", expandLevel = 5),
        power = @Power(importable = true),
        orderBy = "EruptOrg.sort asc"
)
@EruptI18n
@Getter
@Setter
@NoArgsConstructor
public class EruptOrg extends BaseModel {

    @Column(length = AnnotationConst.CODE_LENGTH)
    @EruptField(
            views = @View(title = "组织编码", sortable = true),
            edit = @Edit(title = "组织编码", placeHolder = "保存时自动生成", search = @Search(vague = true), notNull = true)
    )
    @CodeGenerator
    private String code;

    @EruptField(
            views = @View(title = "组织名称", sortable = true),
            edit = @Edit(title = "组织名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @ManyToOne
    @EruptField(
            views = @View(title = "上级组织",column = "name"),
            edit = @Edit(
                    title = "上级组织",
                    type = EditType.REFERENCE_TREE,
                    referenceTreeType = @ReferenceTreeType(pid = "parentOrg.id", expandLevel = 3)
            )
    )
    private EruptOrg parentOrg;

    @EruptField(
            edit = @Edit(
                    title = "显示顺序"
            )
    )
    private Integer sort;


}
