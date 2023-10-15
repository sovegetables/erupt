package com.qamslink.mes.model.upload;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.AttachmentType;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author LiYongGui
 * @create 2023-08-01 下午6:12
 */

@Setter
@Getter
@Entity
@Table(name = "mes_upload_image")
@Erupt(name = "图片",
//        dataProxy = MesUploadImageService.class,
        orderBy = "MesUploadImage.createTime desc",
        filter = @Filter(value = "MesUploadImage.tenantId",
                params = {"and MesUploadImage.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_upload_image set deleted = true where id = ?")
public class MesUploadImage extends HyperModelVo {
    private static final long serialVersionUID = -4295434994046365861L;
    @EruptField(
            views = @View(title = "图片"),
            edit = @Edit(title = "图片", type = EditType.ATTACHMENT,
                    attachmentType = @AttachmentType(fileTypes = {"png", "jpg", "jpeg"})
            )
    )
    private String url;
    private Boolean deleted = false;
}
