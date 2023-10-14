package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesStock;
import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "mes_other_in_detail")
@Erupt(name = "其他入库详情")
public class MesOtherInDetail extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料名称", column = "name"),
                    @View(title = "物料规格", column = "spec"),
                    @View(title = "物料编码", column = "code"),
                    @View(title = "物料单位", column = "unit")
            },
            edit = @Edit(title = "物料", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = {
                    @View(title = "条码编码")
            },
            edit = @Edit(title = "条码编码", notNull = true, search = @Search)
    )
    private String barcodeNum;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "仓库", column = "name"),
            },
            edit = @Edit(title = "仓库", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesWarehouse warehouse;

    @ManyToOne
    @EruptField(
            views = @View(title = "库位", column = "locationCode"),
            edit = @Edit(title = "库位", search = @Search(vague = true), type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"))
    )
    private MesLocation location;

    @EruptField(
            views = {@View(title = "数量")},
            edit = @Edit(title = "数量", notNull = true)
    )
    private BigDecimal quantity;

    @EruptField(
            views = {
                    @View(title = "生产日期")
            },
            edit = @Edit(title = "生产日期")
    )
    private Date productionDate;

}
