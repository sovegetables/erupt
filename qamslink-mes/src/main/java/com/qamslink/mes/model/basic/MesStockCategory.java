package com.qamslink.mes.model.basic;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.annotation.sub_erupt.Tree;
import xyz.erupt.annotation.sub_field.Edit;
import xyz.erupt.annotation.sub_field.EditType;
import xyz.erupt.annotation.sub_field.View;
import xyz.erupt.annotation.sub_field.sub_edit.ReferenceTreeType;
import xyz.erupt.annotation.sub_field.sub_edit.Search;
import xyz.erupt.core.annotation.CodeGenerator;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mes_stock_category")
@Erupt(name = "物料分类",
        tree = @Tree(pid = "parentCategory.id",expandLevel = 3)
//        dataProxy = MesStockCategoryService.class,
)
@Setter
@Getter
public class MesStockCategory extends HyperModel {

    @EruptField(
            views = @View(title = "分类编码"),
            edit = @Edit(title = "分类编码", placeHolder = "保存时自动生成", search = @Search(vague = true), notNull = true)
    )
    @CodeGenerator
    private String code;

    @EruptField(
            views = @View(title = "分类名称"),
            edit = @Edit(title = "分类名称", search = @Search(vague = true), notNull = true)
    )
    private String name;

    @ManyToOne
    @EruptField(
            edit = @Edit(title = "上级分类", type = EditType.REFERENCE_TREE,
                    referenceTreeType = @ReferenceTreeType(pid = "parentCategory.id", expandLevel = 2))
    )
    private MesStockCategory parentCategory;
}
