package com.qamslink.mes.model.production;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.upms.filter.TenantFilter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("1")
@Table(name = "mes_production_schedule_detail")
@Setter
@Getter
@Erupt(name = "已下发生产任务",
        orderBy = "MesProductionScheduleDown.createTime desc",
        filter = @Filter(value = "MesProductionScheduleDown.tenantId",
                params = {"and MesProductionScheduleDown.deleted = false"},
                conditionHandler = TenantFilter.class),
        power = @Power(add = false, delete = false, edit = false)
)
@SQLDelete(sql = "update mes_production_schedule set deleted = true where id = ?")
public class MesProductionScheduleDown extends MesProductionScheduleDetail {
}
