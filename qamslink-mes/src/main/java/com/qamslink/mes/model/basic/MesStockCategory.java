package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Filter;
import xyz.erupt.annotation.sub_erupt.Tree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTreeType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.upms.filter.TenantFilter;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_stock_category")
@Erupt(name = "物料分类",
        tree = @Tree(pid = "parentCategory.id",expandLevel = 3),
//        dataProxy = MesStockCategoryService.class,
        filter = @Filter(value = "MesStockCategory.tenantId",params = {"and MesStockCategory.deleted = false"},
                conditionHandler = TenantFilter.class))
@Setter
@Getter
@SQLDelete(sql = "update mes_stock_category set deleted = true where id = ?")
public class MesStockCategory extends HyperModel {

    @EruptField(
            views = @View(title = "物料分类名称"),
            edit = @Edit(title = "物料分类名称", search = @Search(vague = true), notNull = true)
    )
    private String name;

    @EruptField(
            views = @View(title = "物料分类编码"),
            edit = @Edit(title = "物料分类编码", search = @Search(vague = true), notNull = true)
    )
    private String code;

    @ManyToOne
    @EruptField(
            edit = @Edit(title = "上级分类", type = EditType.REFERENCE_TREE,
                    referenceTreeType = @ReferenceTreeType(pid = "parentCategory.id", expandLevel = 2))
    )
    private MesStockCategory parentCategory;



    private Boolean deleted = false;
}
