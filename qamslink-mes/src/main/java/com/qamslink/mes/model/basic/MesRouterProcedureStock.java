package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "mes_router_procedure_stock")
@Erupt(name = "工序物料")
public class MesRouterProcedureStock extends BaseModel {
    @ManyToOne
    @EruptField(
            views = @View(title = "路由工序id", column = "id")
    )
    private MesRouterWorkingProcedure routerProcedure;

    @ManyToOne
    @EruptField(
            views = @View(title = "物料id", column = "name")
    )
    private MesStock stock;
}
