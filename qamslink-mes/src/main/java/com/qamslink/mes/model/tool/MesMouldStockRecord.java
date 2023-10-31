package com.qamslink.mes.model.tool;

import com.qamslink.mes.model.warehouse.MesLocation;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.Readonly;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_mould_stock_record")
@Setter
@Getter
@Erupt(name = "工具出入库记录",
//        dataProxy = MesMouldStockRecordService.class,
        orderBy = "MesMouldStockRecord.createTime desc")
public class MesMouldStockRecord extends HyperModelVo {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "工具编码", column = "code", highlight = true),
                    @View(title = "工具名称", column = "name")
                    },
            edit = @Edit(title = "工具", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesMould mould;

    @ManyToOne
    @EruptField(
            views = @View(title = "存放库位", column = "locationCode"),
            edit = @Edit(title = "存放库位", readonly = @Readonly, type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"),
                    showBy = @ShowBy(dependField = "type", expr = "value == 1"))
    )
    private MesLocation location;

    @EruptField(
            views = @View(title = "记录类型"),
            edit = @Edit(title = "记录类型", notNull = true, search = @Search, type = EditType.CHOICE, choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "入库", value = "1"),
                            @VL(label = "出库", value = "2")
                    }
            ))
    )
    private Integer type;
}
