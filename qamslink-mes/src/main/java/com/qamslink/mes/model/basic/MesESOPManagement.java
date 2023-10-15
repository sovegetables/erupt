package com.qamslink.mes.model.basic;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.AttachmentType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="mes_esop_management")
@Erupt(name = "ESOP管理",
//        dataProxy = MesESOPManagementService.class,
        orderBy = "MesESOPManagement.createTime desc",
        filter = @Filter(value = "MesESOPManagement.tenantId",
                params = {"and MesESOPManagement.deleted = false"},
                conditionHandler = TenantFilter.class
        )
)
@Data
@SQLDelete(sql = "update mes_esop_management set deleted = true where id = ?")
public class MesESOPManagement extends HyperModelVo {

    @EruptField(
            views = @View(title = "ESOP名称"),
            edit = @Edit(title = "ESOP名称", notNull = true, search = @Search(vague = true))
    )
    private String ESOPName;

    @EruptField(
            views = @View(title = "ESOP编码"),
            edit = @Edit(title = "ESOP编码", notNull = true, search = @Search(vague = true))
    )
    private String ESOPCode;

    @EruptField(
            views = @View(title = "附件上传"),
            edit = @Edit(title = "附件上传", type = EditType.ATTACHMENT,
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
//            ,
//            hasExtra = true
    )
    private List<MesStock> stocks;




    private Boolean deleted = false;

}
