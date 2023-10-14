package com.qamslink.mes.model.warehouse;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "mes_qr_code_print")
@Erupt(name = "二维码打印记录",
//        dataProxy = MesWorkbenchQrCodePrintService.class,
        filter = @Filter(value = "MesQrCodePrint.tenantId",
                params = {"and MesQrCodePrint.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_qr_code_print set deleted = true where id = ?")
public class MesQrCodePrint extends HyperModelCreatorVo {
    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号")
    )
    private String qrCodeTitle;

    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号")
    )
    private String qrCodeContent;

    @EruptField(
            views = @View(title = "租户", show = false)
    )
    private Long tenantId;

    private Boolean deleted = false;
}
