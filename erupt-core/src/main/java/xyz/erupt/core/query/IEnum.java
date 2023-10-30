package xyz.erupt.core.query;

import xyz.erupt.annotation.fun.VLModel;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IEnum<T> {
    T getValue();

    String getValueStr();

    String getLabel();

    class HandlerHelper{
        public static <E extends Enum<?>> List<VLModel>  createFetchHandlers(E[] values){
            return Stream.of(values).filter(type -> type instanceof IEnum).map(type -> {
                        IEnum<?> iEnum = (IEnum<?>) type;
                        return new VLModel(iEnum.getValue() + "", iEnum.getLabel());
                    })
                    .collect(Collectors.toList());
        }
    }
}
