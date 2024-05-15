package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.NumberType;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTableType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.helper.HyperModelVo;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@Table(name="mes_bom", uniqueConstraints={ @UniqueConstraint(columnNames = {"main_stock_id", "version"})})
@Erupt(
        name = "物料清单",
        orderBy = "MesBom.createTime desc",
        power = @Power(importable = true),
        dataProxy = MesBom.InnerProxy.class
)
@Slf4j
public class MesBom extends HyperModelVo {

    @Service
    public static class InnerProxy implements DataProxy<MesBom>{
        @Override
        public void addBehavior(MesBom mesBom) {
            ArrayList<MesBomWorkingProcedure> procedures = new ArrayList<>(1);
            MesBomWorkingProcedure workingProcedure = new MesBomWorkingProcedure();
            workingProcedure.setWorkEndInspectionType(1);
            workingProcedure.setSort(1);
            procedures.add(workingProcedure);
            mesBom.setBomWorkingProcedures(procedures);
            ArrayList<MesBomStock> bomStocks = new ArrayList<>(1);
            MesBomStock bomStock = new MesBomStock();
            bomStock.setParentNum(new BigDecimal("1"));
            bomStocks.add(bomStock);
            mesBom.setStocks(bomStocks);
        }
    }

    public static class VersionGenerator implements CodeGenerator.CodeHandler{
        @Override
        public Object generateCode(Field field, Object value, List<Field> fields, CodeGenerator.KEY[] keys) {
            Map<String, Field> fieldMap = fields.stream()
                    .collect(Collectors.toMap(Field::getName, Function.identity()));
            try {
                Field stock = fieldMap.get("mainStock");
                MesStock o = (MesStock) stock.get(value);
                Field versionField = fieldMap.get("version");
                BigDecimal version = (BigDecimal) versionField.get(value);
                if(version == null){
                    version = new BigDecimal("1.0");
                }
                return version;
            } catch (IllegalAccessException e) {
                log.error("InnerCodeGenerator:", e);
            }
            return null;
        }
    }

    @ManyToOne
    @EruptField(
            views = {
                    @View(title = "物料编码", column = "code", highlight = true),
                    @View(title = "物料名称", column = "name")
            },
            edit = @Edit(title = "物料编码", type = EditType.REFERENCE_TABLE,
                    referenceTableType = @ReferenceTableType(label = "code"),
                    notNull = true, search = @Search(vague = true))
    )
    private MesStock mainStock;

//    @EruptField(
//            views = @View(title = "是否默认BOM"),
//            edit = @Edit(title = "是否默认BOM", boolType = @BoolType(trueText = "是", falseText = "否"))
//    )
//    private Boolean isDefault = true;

    @EruptField(
            views = @View(title = "版本号", highlight = true),
            edit = @Edit(title = "版本号",
                    placeHolder = "保存时自动生成",
                    numberType = @NumberType(min = 1, step = 0.1)
                    )
    )
    @CodeGenerator(handler = VersionGenerator.class)
    private BigDecimal version;

    @JoinColumn(name = "bom_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "物料明细", show = false),
            edit = @Edit(title = "物料明细", type = EditType.TAB_TABLE_ADD)
    )
    private List<MesBomStock> stocks;

    @JoinColumn(name = "bom_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @EruptField(
            views = @View(title = "工序明细", show = false),
            edit = @Edit(title = "工序明细", type = EditType.TAB_TABLE_ADD)
    )
    private List<MesBomWorkingProcedure> bomWorkingProcedures;

    private Boolean deleted = false;
}
