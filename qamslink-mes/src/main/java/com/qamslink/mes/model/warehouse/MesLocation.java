package com.qamslink.mes.model.warehouse;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.sub_erupt.LinkTree;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.core.util.EruptSpringUtil;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@Table(name = "mes_location")
@Erupt(
        name = "库位管理",
        linkTree = @LinkTree(field = "warehouse"),
        power = @Power(importable = true),
        orderBy = "MesLocation.createTime desc",
        dataProxy = MesLocation.InnerDataProxy.class
        )
@Slf4j
public class MesLocation extends HyperModelVo {

    public static class InnerDataProxy implements DataProxy<MesLocation>{
        @Override
        public void beforeAdd(MesLocation mesLocation) {
            DataProxy.super.beforeAdd(mesLocation);
        }
    }

    public static class CodeGeneratorHandler implements CodeGenerator.CodeHandler {

        @Override
        public Object generateCode(Field field, Object value, List<Field> fields, CodeGenerator.KEY[] keys) {
            Map<String, Field> fieldMap = fields.stream()
                    .collect(Collectors.toMap(Field::getName, Function.identity()));
            try {
                Field warehouseField = fieldMap.get("warehouse");
                MesWarehouse warehouse = (MesWarehouse) warehouseField.get(value);
                EruptDao eruptDao = EruptSpringUtil.getBean(EruptDao.class);
                HashMap<String, Object> queryParams = new HashMap<>();
                queryParams.put("id", warehouse.getId());
                warehouse= eruptDao.queryEntity(warehouse.getClass(), "id = :id", queryParams);
                Field storeCodeField = fieldMap.get("storeCode");
                String storeCode = (String) storeCodeField.get(value);
                Field posCodeField = fieldMap.get("posCode");
                String posCode = (String) posCodeField.get(value);
                return warehouse.getCode() + "-" + storeCode + '-' + posCode;
            } catch (IllegalAccessException e) {
                log.error("InnerCodeGenerator:", e);
            }
            return null;
        }
    }

    @EruptField(
            views = @View(title = "库位条码"),
            edit = @Edit(title = "库位条码", search = @Search(vague = true),
                    placeHolder = "保存时自动生成")
    )
    @CodeGenerator(handler = CodeGeneratorHandler.class)
    private String locationCode;

    @ManyToOne
    @EruptField(
            views = {@View(title = "所属仓库", column = "name"), @View(title = "仓库编码", column = "code")},
            edit = @Edit(title = "所属仓库", notNull = true, type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(),
                    search = @Search(vague = true))
    )
    private MesWarehouse warehouse;

    @EruptField(
            views = @View(title = "储位编码"),
            edit = @Edit(title = "储位编码", notNull = true)
    )
    private String storeCode;
    @EruptField(
            views = @View(title = "储位名称"),
            edit = @Edit(title = "储位名称", notNull = true)
    )
    private String storeName;
    @EruptField(
            views = @View(title = "货位编码"),
            edit = @Edit(title = "货位编码", notNull = true)
    )
    private String posCode;
    @EruptField(
            views = @View(title = "货位名称"),
            edit = @Edit(title = "货位名称", notNull = true)
    )
    private String posName;

    @EruptField(
            views = @View(title = "描述"),
            edit = @Edit(title = "描述")
    )
    private String description;
}
