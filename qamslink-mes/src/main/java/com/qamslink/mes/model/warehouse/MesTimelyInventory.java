package com.qamslink.mes.model.warehouse;

import com.google.gson.internal.LinkedTreeMap;
import com.qamslink.mes.core.EruptDataAdapterService;
import com.qamslink.mes.model.basic.MesStock;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.query.Condition;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.EruptDataProcessor;
import xyz.erupt.core.invoke.DataProcessorManager;
import xyz.erupt.core.query.EruptQuery;
import xyz.erupt.core.view.EruptModel;
import xyz.erupt.core.view.Page;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.service.EruptUserService;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static xyz.erupt.annotation.config.QueryExpression.EQ;
import static xyz.erupt.annotation.config.QueryExpression.LIKE;

@Entity
@Getter
@Setter
@Erupt(
        name = "即时库存",
        power = @Power(add = false, edit = false, delete = false),
        dataProxy = MesTimelyInventory.InnerDataProxy.class,
        filter = @Filter(
                value = "MesTimelyInventory.tenantId",
                conditionHandler = TenantFilter.class
        )
)
@EruptDataProcessor("mes-timely-inventory")
public class MesTimelyInventory extends BaseModel {

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料名称", column = "name"),
                    @View(title = "物料编码", column = "code")
            },
            edit = @Edit(title = "物料", show = false, search = @Search, type = EditType.REFERENCE_TABLE)
    )
    private MesStock stock;

    @EruptField(
            views = {@View(title = "条码编码")},
            edit = @Edit(title = "条码编码", search = @Search(vague = true))
    )
    private String barcodeNum;

    @EruptField(
            views = {@View(title = "实时库存")}
    )
    private BigDecimal timelyCapacity;

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "仓库", column = "name"),
            },
            edit = @Edit(title = "仓库", show = false, search = @Search(vague = true), type = EditType.REFERENCE_TABLE)
    )
    private MesWarehouse warehouse;

    @Transient
    private Long stockId;

    @Transient
    private String stockCode;

    @Transient
    private String stockName;

    @Transient
    private String warehouseName;


    static {
        DataProcessorManager.register("mes-timely-inventory", MesTimelyInventoryProcessor.class);
    }

    @Service
    public static class MesTimelyInventoryProcessor extends EruptDataAdapterService {

        @Autowired
        private EruptUserService eruptUserService;

//        @Resource
//        private MesStockFlowDetailService mesStockFlowDetailService;

        @Data
        public static class QueryDTO {

            private int pageNum;

            private int pageSize;

            private String stockId;

            private String warehouseId;

            private String barcodeNum;



            public int getOffset() {
                return (pageNum - 1) * pageSize;
            }
        }

        @Override
        public Page queryList(EruptModel eruptModel, Page page, EruptQuery eruptQuery) {
            QueryDTO queryDTO = new QueryDTO();
            queryDTO.setPageNum(page.getPageIndex());
            queryDTO.setPageSize(page.getPageSize());
//            queryDTO.setTenantId(getTenantId());
            List<Condition> conditions = eruptQuery.getConditions();
            Map<String, Consumer<String>> propertyMap = new HashMap<>();
            propertyMap.put("stock", queryDTO::setStockId);
            propertyMap.put("warehouse", queryDTO::setWarehouseId);
            propertyMap.put("barcodeNum", queryDTO::setBarcodeNum);
            for (Condition condition : conditions) {
                String conditionKey = condition.getKey();
                Object queryString = condition.getValue();
                if (condition.getExpression() == EQ || condition.getExpression() == LIKE) {
                    Consumer<String> propertySetter = propertyMap.get(conditionKey);
                    if (propertySetter != null) {
                        if (conditionKey.equals("stock") || conditionKey.equals("warehouse")) {
                            queryString = ((LinkedTreeMap<?, ?>) condition.getValue()).get("id").toString();
                        }
                        propertySetter.accept(queryString.toString());
                    }
                }
            }
//            com.baomidou.mybatisplus.extension.plugins.pagination.Page<MesTimelyInventory> pageInfo = mesStockFlowDetailService.selectPage(queryDTO);
//            List<MesTimelyInventory> records = pageInfo.getRecords();
//            if (CollUtil.isNotEmpty(records)) {
//                List<Map<String, Object>> list = records.stream()
//                        .map(BeanUtil::beanToMap)
//                        .collect(Collectors.toList());
//                list.forEach(i -> {
//                    i.put("stock_id", i.get("stockId"));
//                    i.put("stock_name", i.get("stockName"));
//                    i.put("stock_code", i.get("stockCode"));
//                    i.put("warehouse_name", i.get("warehouseName"));
//                    i.put("tenantId", getTenantId());
//                });
//                page.setList(list);
//            } else {
//                page.setList(new ArrayList<>());
//            }
//            page.setTotal(pageInfo.getTotal());
            return page;
        }

//        private Long getTenantId() {
//            return eruptUserService.getCurrentEruptUser().getTenantId();
//        }
    }

    public static class InnerDataProxy implements DataProxy<MesTimelyInventory> {
    }
}
