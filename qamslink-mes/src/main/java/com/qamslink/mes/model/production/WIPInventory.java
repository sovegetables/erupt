package com.qamslink.mes.model.production;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.qamslink.mes.core.EruptDataAdapterService;
import lombok.Data;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.query.Condition;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.EruptDataProcessor;
import xyz.erupt.core.invoke.DataProcessorManager;
import xyz.erupt.core.query.EruptQuery;
//import xyz.erupt.core.service.EruptDataAdapterService;
import xyz.erupt.core.view.EruptModel;
import xyz.erupt.core.view.Page;
import xyz.erupt.jpa.model.BaseModel;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.service.EruptUserService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static xyz.erupt.annotation.config.QueryExpression.EQ;
import static xyz.erupt.annotation.config.QueryExpression.LIKE;

@Erupt(name = "在制品库存",
        dataProxy = WIPInventory.InnerDataProxy.class,
        orderBy = "WIPInventory.updateTime desc",
        filter = @Filter(
                value = "WIPInventory.tenantId",
                conditionHandler = TenantFilter.class
        ),
        power = @Power(add = false, delete = false, edit = false)
)
@EruptDataProcessor("WIP-inventory")
public class WIPInventory extends BaseModel {

    @EruptField(
            views = {
                    @View(title = "物料名称")
            },
            edit = @Edit(title = "物料名称", notNull = true, search = @Search)
    )
    private String stockName;

    @EruptField(
            views = {
                    @View(title = "物料编码")
            },
            edit = @Edit(title = "物料编码", notNull = true, search = @Search)
    )
    private String stockCode;

    @EruptField(
            views = {
                    @View(title = "物料规格")
            },
            edit = @Edit(title = "物料规格", notNull = true)
    )
    private String stockSpec;

    @EruptField(
            views = {
                    @View(title = "物料单位")
            },
            edit = @Edit(title = "物料单位", notNull = true)
    )
    private String stockUnit;

    @EruptField(
            views = {
                    @View(title = "工序")
            },
            edit = @Edit(title = "工序", notNull = true, search = @Search)
    )
    private String workingProcedureName;

    @EruptField(
            views = {
                    @View(title = "工序编码")
            },
            edit = @Edit(title = "工序编码", notNull = true, search = @Search)
    )
    private String workingProcedureCode;

    @EruptField(
            views = {
                    @View(title = "在制数量")
            },
            edit = @Edit(title = "在制数量", notNull = true)
    )
    private BigDecimal quantity;



    static {
        DataProcessorManager.register("WIP-inventory", WIPInventoryDataProcessor.class);
    }

    @Service
    public static class WIPInventoryDataProcessor extends EruptDataAdapterService {

        @Resource
        private EruptUserService eruptUserService;

//        @Resource
//        private MesWorkStartService mesWorkStartService;

        @Data
        public static class QueryDTO {

            private int pageNum;

            private int pageSize;

            private String workingProcedureCode;

            private String workingProcedureName;

            private String stockName;

            private String stockCode;

            private Long tenantId;

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

            Map<String, Consumer<String>> propertyMap = new HashMap<>();
            propertyMap.put("stockName", queryDTO::setStockName);
            propertyMap.put("stockCode", queryDTO::setStockCode);
            propertyMap.put("workingProcedureName", queryDTO::setWorkingProcedureName);
            propertyMap.put("workingProcedureCode", queryDTO::setWorkingProcedureCode);

            List<Condition> conditions = eruptQuery.getConditions();
            for (Condition condition : conditions) {
                String conditionKey = condition.getKey();
                String queryString = condition.getValue().toString();
                if (condition.getExpression() == EQ || condition.getExpression() == LIKE) {
                    Consumer<String> propertySetter = propertyMap.get(conditionKey);
                    if (propertySetter != null) {
                        propertySetter.accept(queryString);
                    }
                }
            }
//            com.baomidou.mybatisplus.extension.plugins.pagination.Page<WIPInventoryEntity> entityPage = mesWorkStartService.selectPage(queryDTO);
//            List<WIPInventoryEntity> records = entityPage.getRecords();
//            if(CollUtil.isNotEmpty(records)){
//                List<Map<String, Object>> list = records.stream()
//                        .map(BeanUtil::beanToMap)
//                        .collect(Collectors.toList());
//                page.setList(list);
//            }else {
//                page.setList(new ArrayList<>());
//            }
//            page.setTotal(entityPage.getTotal());
            return page;
        }

//        private Long getTenantId() {
//            return eruptUserService.getCurrentEruptUser().getTenantId();
//        }
    }

    public static class InnerDataProxy implements DataProxy<WIPInventory> {}
}
