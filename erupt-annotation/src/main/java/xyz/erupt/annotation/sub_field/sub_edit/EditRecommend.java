package xyz.erupt.annotation.sub_field.sub_edit;

import xyz.erupt.annotation.config.Comment;

public @interface EditRecommend {
    @Comment("依赖表字段名")
    String dependModel() default "";
    @Comment("依赖表主键名")
    String dependModelPKey() default "";
    @Comment("依赖字段名")
    String dependField();
    @Comment("显示条件表达式，支持变量：value 该值表示依赖字段的值")
    String expr() default "";
}
