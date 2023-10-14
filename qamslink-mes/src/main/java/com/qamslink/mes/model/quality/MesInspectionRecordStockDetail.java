package com.qamslink.mes.model.quality;

import com.qamslink.mes.model.basic.MesStock;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "mes_inspection_record_stock_detail")
@Getter
@Setter
@Erupt(name = "物料检验模板物料详情",
//        dataProxy = MesInspectionRecordStockDetailService.class,
        orderBy = "MesInspectionRecordStockDetail.createTime desc",
        filter = @Filter(value = "MesInspectionRecordStockDetail.tenantId",
                params = {"and MesInspectionRecordStockDetail.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_inspection_record_stock_detail set deleted = true where id = ?")
public class MesInspectionRecordStockDetail extends HyperModelCreatorVo {
    @ManyToOne
    @EruptField(
            views = @View(title = "检验记录", column = "sn"),
            edit = @Edit(title = "检验记录", notNull = true, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesInspectionRecord inspectionRecord;

    @ManyToOne
    @EruptField(
            views = {@View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code")},
            edit = @Edit(title = "物料名称", search = @Search(vague = true), type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"))
    )
    private MesStock stock;

    @EruptField(
            views = @View(title = "物料数量"),
            edit = @Edit(title = "物料数量",  type = EditType.NUMBER))
    private BigDecimal num;

    @EruptField
    private String barcodeNum;

    @EruptField(
            views = @View(title = "租户", show = false, columnShowed = false)
    )
    private Long tenantId;

    private Boolean deleted = false;
}
