package com.qamslink.mes.model.basic;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.DateType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.model.EruptUserVo;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mes_process_route")
@Setter
@Getter
@Erupt(name = "工艺路线",
//        dataProxy = MesProcessRouteService.class,
        orderBy = "MesProcessRoute.createTime desc",
        power = @Power(importable = true),
        filter = @Filter(value = "MesProcessRoute.tenantId", params = {"and MesProcessRoute.deleted = false"}, conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_process_route set deleted = true where id = ?")
public class MesProcessRoute extends BaseModel {

    @EruptField(
            views = @View(title = "工艺名称"),
            edit = @Edit(title = "工艺名称", notNull = true)
    )
    private String name;

    @EruptField(
            views = @View(title = "工艺编码"),
            edit = @Edit(title = "工艺编码", notNull = true)
    )
    private String code;

    @ManyToOne
    @EruptField(
            views ={@View(title = "物料名称",column = "name"),
                    @View(title = "物料编码",column = "code")},
            edit = @Edit(title = "物料",notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;


    @JoinColumn(name = "process_route_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "工序排序"),
            edit = @Edit(title = "工序排序",type = EditType.TAB_TABLE_ADD,notNull = true)
    )
    private List<MesProcessRouteDetail> MesProcessRouteDetails;


    @ManyToOne
    @EruptField(
            views = @View(title = "创建人", width = "100px", column = "name"),
            edit = @Edit(title = "创建人",show = false, readonly = @Readonly, type = EditType.REFERENCE_TABLE)
    )
    private EruptUserVo createUser;

    @EruptField(
            views = @View(title = "创建时间", sortable = true),
            edit = @Edit(title = "创建时间", show = false, readonly = @Readonly, dateType = @DateType(type = DateType.Type.DATE_TIME))
    )
    private Date createTime;



    private Boolean deleted = false;


}
