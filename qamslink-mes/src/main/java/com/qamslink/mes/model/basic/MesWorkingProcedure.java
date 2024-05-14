package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "mes_working_procedure")
@Erupt(name = "工序定义",
        linkTree = @LinkTree(field = "procedureCategory", fieldClass = "MesProcedureCategory"),
        orderBy = "MesWorkingProcedure.createTime desc",
        power = @Power(importable = true)
        )
public class MesWorkingProcedure extends HyperModelVo {

    @EruptField(
            views = @View(title = "工序编码", highlight = true),
            edit = @Edit(title = "工序编码", placeHolder = "保存时自动生成", notNull = true,
                    search = @Search(vague = true))
    )
    @CodeGenerator
    private String code;

    @EruptField(
            views = @View(title = "工序名称"),
            edit = @Edit(title = "工序名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "工序类型", column = "name"),
            edit = @Edit(title = "工序类型",  type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    @ManyToOne
    private MesProcedureCategory procedureCategory;

//    @EruptField(
//            views = @View(title = "状态"),
//            edit = @Edit(title = "状态", notNull = true, boolType = @BoolType(trueText = "启用", falseText = "停用"))
//    )
//    private Boolean status = true;

//    @EruptField(
//            views = @View(title = "一码到底", show = false),
//            edit = @Edit(title = "一码到底", notNull = true, boolType = @BoolType(trueText = "是", falseText = "否"), show = false)
//    )
//    private Boolean isOneCode = false;
//
//    public static final int ISSUE_TYPE_UP = 1;
//    public static final int ISSUE_TYPE_DOWN = 2;

//    @EruptField(
//            views = @View(title = "发料方式"),
//            edit = @Edit(title = "发料方式", type = EditType.CHOICE, notNull = true,
//                    choiceType = @ChoiceType(vl = {
//                            @VL(label = "直接领料", value = ISSUE_TYPE_UP +""),
//                            @VL(label = "直接倒冲", value = ISSUE_TYPE_DOWN + "")
//                    }))
//    )
//    private Integer issueType = 2;
//
//    public static final int TYPE_OUT = 2;
//    public static final int TYPE_IN = 1;

//    @EruptField(
//            views = @View(title = "生产方式"),
//            edit = @Edit(title = "生产方式", type = EditType.CHOICE, notNull = true,
//                    choiceType = @ChoiceType(vl = {
//                            @VL(label = "自制", value = "1"),
//                            @VL(label = "委外", value = "2")
//                    }))
//    )
//    private Integer type = 1;

//    @EruptField(
//            views = @View(title = "首检"),
//            edit = @Edit(title = "首检", notNull = true, boolType = @BoolType(trueText = "是", falseText = "否"))
//    )
//    private Boolean firstInspection = false;

    @EruptField(
            views = @View(
                    title = "描述"
            ),
            edit = @Edit(
                    title = "描述",
                    type = EditType.TEXTAREA
            )
    )
    private String description;

//    @EruptField(
//            views = @View(title = "作业指导书", type=ViewType.ATTACHMENT),
//            edit = @Edit(title = "作业指导书上传", type = EditType.ATTACHMENT,
//                    attachmentType = @AttachmentType(fileTypes = "pdf"))
//    )
//    private String attachment;

    private Boolean deleted = false;

}
