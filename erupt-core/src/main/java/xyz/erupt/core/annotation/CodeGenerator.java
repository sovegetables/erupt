package xyz.erupt.core.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface CodeGenerator {
    Class<? extends CodeHandler> handler() default NOT.class;

    KEY[] params() default {};

    interface CodeHandler{
       Object generateCode(Field field, Object value, List<Field> fields, KEY[] keys);
    }


    @interface KEY {
        String key();

        String value();

        String desc() default "";
    }

    class NOT implements CodeHandler{
        @Override
        public Object generateCode(Field field, Object value, List<Field> fields, KEY[] keys) {
            return null;
        }
    }
}
