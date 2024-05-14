package com.qamslink.mes.model.basic;

import lombok.Data;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.AttachmentType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="mes_esop_management")
@Erupt(name = "ESOP管理",
        orderBy = "MesESOPManagement.createTime desc"
)
@Data
public class MesESOPManagement extends HyperModelVo {

    @EruptField(
            views = @View(title = "编码"),
            edit = @Edit(title = "编码", placeHolder = "保存时自动生成",notNull = true, search = @Search(vague = true))
    )
    @CodeGenerator
    private String ESOPCode;

    @EruptField(
            views = @View(title = "名称"),
            edit = @Edit(title = "名称", notNull = true, search = @Search(vague = true))
    )
    private String ESOPName;

    @EruptField(
            views = @View(title = "附件"),
            edit = @Edit(title = "附件", type = EditType.ATTACHMENT,
                    attachmentType = @AttachmentType(fileTypes = "pdf")
            )
    )
    private String attachment;

    @ManyToMany
    @JoinTable(name = "mes_esop_management_stock",
            joinColumns = @JoinColumn(name = "esop_management_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id", referencedColumnName = "id"))
    @EruptField(
            views = @View(title = "物料",show = false),
            edit = @Edit(title = "物料", type = EditType.TAB_TABLE_REFER)
    )
    private List<MesStock> stocks;
}
