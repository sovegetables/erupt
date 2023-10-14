package com.qamslink.mes.model.quality;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_inspection_mould_detail")
@Getter
@Setter
@Erupt(name = "检验模板详情")
public class MesInspectionMouldDetail extends BaseModel {

    @ManyToOne
    @EruptField(
            views = @View(title = "检验模板", column = "name")
    )
    private MesInspectionMould inspectionMould;

    @ManyToOne
    @EruptField(
            views = @View(title = "检验项", column = "name")
    )
    private MesInspectionItem inspectionItem;
}
