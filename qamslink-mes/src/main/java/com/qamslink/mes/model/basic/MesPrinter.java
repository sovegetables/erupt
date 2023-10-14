package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mes_printer")
@Setter
@Getter
@Erupt(name = "打印机",
//        dataProxy = MesPrinterService.class,
        orderBy = "MesPrinter.createTime desc",
        filter = @Filter(value = "MesPrinter.tenantId",
                params = {"and MesPrinter.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_printer set deleted = true where id = ?")
public class MesPrinter extends TenantCreatorModel {

    @EruptField(
            views = @View(title = "名称"),
            edit = @Edit(title = "名称", notNull = true, search = @Search(vague = true))
    )
    private String name;

    @EruptField(
            views = @View(title = "ip"),
            edit = @Edit(title = "ip", notNull = true)
    )
    private String ip;

    @EruptField(
            views = @View(title = "端口"),
            edit = @Edit(title = "端口", notNull = true)
    )
    private String port;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注" ,type = EditType.TEXTAREA)
    )
    private String remark;

    private Boolean deleted = false;
}
