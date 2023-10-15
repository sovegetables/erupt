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
import xyz.erupt.upms.helper.HyperModelVo;
import xyz.erupt.upms.model.EruptUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_workshop")
@Getter
@Setter
@Erupt(name = "车间管理",
//        dataProxy = MesWorkshopService.class,
        filter = @Filter(value = "MesWorkshop.tenantId",
                params = {"and MesWorkshop.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_workshop set deleted = true where id = ?")
public class MesWorkshop extends HyperModelVo {

    @EruptField(
            views = @View(title = "车间名称"),
            edit = @Edit(title = "车间名称", notNull = true, search = @Search(vague = true))
    )
    private String name;
    @ManyToOne
    @EruptField(
            views = @View(title = "负责人", column = "name"),
            edit = @Edit(title = "负责人",
                    type = EditType.REFERENCE_TABLE,
                    search = @Search(vague = true))
    )
    private EruptUser leaderUser;
    @EruptField(
            views = @View(title = "车间简介"),
            edit = @Edit(title = "车间简介")
    )
    private String description;
    private Boolean deleted = false;
}
