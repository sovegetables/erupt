package com.qamslink.mes.model.warehouse;

import com.google.gson.internal.LinkedTreeMap;
import com.qamslink.mes.core.EruptDataAdapterService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.query.Condition;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.ViewType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.EruptDataProcessor;
import xyz.erupt.core.invoke.DataProcessorManager;
import xyz.erupt.core.query.EruptQuery;
import xyz.erupt.core.view.EruptModel;
import xyz.erupt.core.view.Page;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "mes_production_in_detail")
@Erupt(name = "产品入库单",
//        dataProxy = MesProductionInDetailService.class,
        power = @Power(viewDetails = false, edit = false, add = false, delete = false))
@EruptDataProcessor("mes-production")
public class MesProductionInDetail extends BaseModel{

    @EruptField(
            views = @View(title = "产品入库单号"),
            edit = @Edit(title = "产品入库单号", notNull = true, show = false, search = @Search(vague = true))
    )
    private String productionCode;

    @EruptField(
            views = @View(title = "条码编号"),
            edit = @Edit(title = "条码编号", notNull = true, show = false, search = @Search(vague = true))
    )
    private String barcodeNum;

    @Transient
    @EruptField(
            views = @View(title = "物料名称",type = ViewType.TEXT)
    )
    private String stockName;

    @Transient
    @EruptField(
            views = @View(title = "物料编码",type = ViewType.TEXT),
            edit = @Edit(title = "物料编码", show = false, search = @Search(vague = true))
    )
    private String stockCode;

    @Transient
    @EruptField(
            views = @View(title = "入库数量",type = ViewType.TEXT)
    )
    private String inCapacity;

    @ManyToOne
    @Transient
    @EruptField(
            views = {
                    @View(title = "仓库", column = "name"),
            },
            edit = @Edit(title = "仓库", show = false, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesWarehouse mesWarehouse;

    @Transient
    @EruptField(
            views = @View(title = "库位",type = ViewType.TEXT)
    )
    private String locationCode;

    @Transient
    @EruptField(
            views = @View(title = "生产工单号", type = ViewType.TEXT),
            edit = @Edit(title = "生产工单号", show = false, search = @Search(vague = true))
    )
    private String workOrderNo;

    @Transient
    @EruptField(
            views = @View(title = "报工人员",type = ViewType.TEXT, show = false)
    )
    private String reportName;

    @Transient
    @EruptField(
            views = @View(title = "入库时间",type = ViewType.DATE_TIME),
            edit = @Edit(title = "入库时间", show = false, search = @Search(vague = true))
    )
    private Date inTime;

    @Transient
    @EruptField(
            views = @View(title = "生产设备",type = ViewType.TEXT, show = false)
    )
    private String equipmentName;

    @Transient
    @EruptField(
            views = @View(title = "入库人员",type = ViewType.TEXT)
    )
    private String depositor;


    @Transient
    @EruptField(
            views = @View(title = "工单数量",type = ViewType.TEXT, show = false)
    )
    private String orderCount;

    static {
        DataProcessorManager.register("mes-production", InnerDataProcessor.class);
    }

    @Service
    public static class InnerDataProcessor extends EruptDataAdapterService {

//        @Resource
//        private MesWorkOrderService mesWorkOrderService;

        @Override
        public Object findDataById(EruptModel eruptModel, Object id) {
            return null;
        }

        @Override
        public Page queryList(EruptModel eruptModel, Page page, EruptQuery eruptQuery) {
            List<Condition> conditions = eruptQuery.getConditions();
            HashMap<String, Object> params = new HashMap<>();
            for (Condition condition : conditions) {
                String conditionKey = condition.getKey();
                switch (condition.getExpression()) {
                    case EQ:
                        if("mesWarehouse".equals(conditionKey)){
                            params.put("warehouseId", ((LinkedTreeMap) condition.getValue()).get("id"));
                        }
                    case LIKE:
                        params.put(conditionKey, condition.getValue());
                    case RANGE:
                        if("inTime".equals(conditionKey)){
                            ArrayList timeList = (ArrayList) condition.getValue();
                            if(timeList.size() == 2){
                                params.put("startTime", timeList.get(0));
                                params.put("endTime", timeList.get(1));
                            }
                        }
                    case IN:
                        break;
                }
            }
//            com.baomidou.mybatisplus.extension.plugins.pagination.Page<Map<String, Object>> inventoryPage = mesWorkOrderService
//                    .getStockInfoByJustInTimeInventory(params, page.getPageIndex(), page.getPageSize());
//            List<Map<String, Object>> records = inventoryPage.getRecords();
//            List<Map<String, Object>> list = records.stream().peek(item -> {
//                item.put("barcodeNum", item.get("barcodeNum"));
//                item.put("stockName", item.get("stockName"));
//                item.put("workOrderNo", item.get("workOrderCode"));
//                item.put("taskNo", item.get("taskCode"));
//                item.put("stockCode", item.get("stockCode"));
//                item.put("inCapacity", item.get("inCapacity"));
//                item.put("inTime", item.get("inTime"));
//                item.put("orderCount", item.get("capacity"));
//                item.put("equipmentName", item.get("equipmentName"));
//                item.put("productionStaff", item.get("productionStaff"));
//                item.put("mesWarehouse_name", item.get("warehouseName"));
//                item.put("locationCode", item.get("locationCode"));
//                item.put("depositor", item.get("depositor"));
//                item.put("reportName", item.get("reportName"));
//                item.put("productionCode", item.get("productionCode"));
//                item.put("status",  Optional.ofNullable(item.get("status")).orElse("0"));
//            }).collect(Collectors.toList());
//            page.setList(list);
//            page.setTotal(inventoryPage.getTotal());
            return page;
        }
    }
}
