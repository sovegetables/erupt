package xyz.erupt.upms.filter;

import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.FilterHandler;

@Component
public class TenantFilter implements FilterHandler {

    @Override
    public String filter(String condition, String[] params) {
        StringBuilder sql = new StringBuilder("1=1");
        if(params != null) {
            for (String param : params) {
                sql.append(" ").append(param);
            }
        }
        return sql.toString();
    }
}
