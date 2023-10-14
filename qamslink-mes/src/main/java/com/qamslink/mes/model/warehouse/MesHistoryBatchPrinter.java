package com.qamslink.mes.model.warehouse;

import com.qamslink.mes.model.basic.MesStock;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_field.*;
import xyz.erupt.annotation.sub_field.sub_edit.*;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.helper.HyperModelCreatorVo;
import xyz.erupt.upms.helper.TenantCreatorModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "mes_history_batch_printer")
@Erupt(name = "历史产品条码记录",
//        dataProxy = MesHistoryBatchPrinterService.class,
        orderBy = "MesHistoryBatchPrinter.createTime desc",
        filter = @Filter(value = "MesHistoryBatchPrinter.tenantId",
                params = {"and MesHistoryBatchPrinter.deleted = false"},
                conditionHandler = TenantFilter.class))
@SQLDelete(sql = "update mes_history_batch_printer set deleted = true where id = ?")
public class MesHistoryBatchPrinter extends TenantCreatorModel {

    @ManyToOne
    @EruptField(
            views = @View(title = "产品", column = "name"),
            edit = @Edit(title = "产品", notNull = true, type = EditType.REFERENCE_TABLE,readonly = @Readonly)
    )
    private MesStock stock;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "仓库", column = "name")
            },
            edit = @Edit(title = "仓库", notNull = true, search = @Search(vague = true),
                    type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "name"), readonly = @Readonly)

    )
    private MesWarehouse warehouse;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "库位", column = "locationCode")
            },
            edit = @Edit(title = "库位", search = @Search(vague = true), notNull = true,
                    type = EditType.REFERENCE_TABLE, referenceTableType = @ReferenceTableType(label = "locationCode"),readonly = @Readonly)

    )
    private MesLocation location;

    @EruptField(
            views = @View(title = "状态"),
            edit = @Edit(title = "状态", type = EditType.CHOICE,
                    readonly = @Readonly,
                    choiceType = @ChoiceType(
                    vl = {
                            @VL(label = "未入库", value = "0"),
                            @VL(label = "已入库", value = "1")
                    }
            ))
    )
    private Integer status = 1;

    @EruptField(
            views = @View(title = "打码总数量")
    )
    private BigDecimal sumTotal;

    @EruptField(
            views = @View(title = "单位包装数量"),
            edit = @Edit(title = "单位包装数量", notNull = true, numberType = @NumberType(min = 1),readonly = @Readonly)
    )
    private BigDecimal unitNum;

    @EruptField(
            views = @View(title = "生产日期"),
            edit = @Edit(title = "生产日期")
    )
    private Date manufactureDate;

    @EruptField(
            views = @View(title = "批次号"),
            edit = @Edit(title = "批次号")
    )
    private String batchNumber;

    @EruptField(
            views = @View(title = "备注"),
            edit = @Edit(title = "备注", type = EditType.TEXTAREA)
    )
    private String remark;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "history_batch_printer_id")
    @EruptField(
            views = @View(title = "详情"),
            edit = @Edit(title = "详情", type = EditType.TAB_TABLE_ADD
            )
    )
    private List<MesHistoryBatchPrintDetail> historyBatchPrintDetails;

    @Transient
    @EruptField(
            views = @View(title = "打印", type = ViewType.LINK)
    )
    private String url;
    private Boolean deleted = false;
}
