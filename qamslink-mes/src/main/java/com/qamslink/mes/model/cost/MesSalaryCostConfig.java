package com.qamslink.mes.model.cost;

import com.qamslink.mes.model.basic.MesStock;
import com.qamslink.mes.model.basic.MesWorkingProcedure;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mes_salary_cost_config")
@Getter
@Setter
@Erupt(name = "人工成本配置",
//        dataProxy = MesSalaryCostConfigService.class,
        orderBy = "MesSalaryCostConfig.createTime desc",
        power = @Power(viewDetails = false),
        filter = @Filter(value = "MesSalaryCostConfig.tenantId",
                params = {"and MesSalaryCostConfig.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_salary_cost_config set deleted = true where id = ?")
public class MesSalaryCostConfig extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料编码", column = "code"),
                    @View(title = "物料名称", column = "name"),
            },
            edit = @Edit(title = "物料", notNull = true, type = EditType.REFERENCE_TABLE, search = @Search(vague = true))
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = {@View(title = "工序名称", column = "name"),
                    @View(title = "工序编码", column = "code"),

            },
            edit = @Edit(title = "工序名称", notNull = true,
                    search = @Search(vague = true), type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType)
    )
    private MesWorkingProcedure workingProcedure;

    @EruptField(
            views = @View(title = "标准工时单价"),
            edit = @Edit(title = "标准工时单价", notNull = true)
    )
    private BigDecimal standardHourlyRate;

    @EruptField(
            views = @View(title = "实际工时单价"),
            edit = @Edit(title = "实际工时单价", notNull = true)
    )
    private BigDecimal actualHourlyRate;

    @EruptField(
            views = @View(title = "创建人"),
            edit = @Edit(title = "创建人", show = false, editShow = false)
    )
    private String createBy;

    @EruptField(
            views = @View(title = "创建时间"),
            edit = @Edit(title = "创建时间", show = false, editShow = false)
    )
    private Date createTime;

    @EruptField
    private Long createUserId;



    @EruptField
    private Boolean deleted;

}
