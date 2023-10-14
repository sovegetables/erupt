package xyz.erupt.annotation.sub_erupt;

import xyz.erupt.annotation.config.Comment;

public @interface LinkTree {

    @Comment("树字段")
    String field();

    @Comment("表格的数据是否必须依赖树节点")
    boolean dependNode() default false;

    /**
     * 配置该注解后，可以直接在树中新增、编辑
     */
    @Comment("树字段类名")
    String fieldClass() default "";
}
