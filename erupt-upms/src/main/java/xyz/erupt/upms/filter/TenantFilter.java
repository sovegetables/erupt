package xyz.erupt.upms.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.erupt.annotation.fun.FilterHandler;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.service.EruptUserService;

@Component
public class TenantFilter implements FilterHandler {

    @Autowired
    private EruptUserService eruptUserService;
    @Override
    public String filter(String condition, String[] params) {
        EruptUser currentUser = eruptUserService.getCurrentEruptUser();
        StringBuilder sql;
        if(currentUser.getIsSuperAdmin()) {
            sql = new StringBuilder("1=1");
        }else {
            sql = new StringBuilder(condition + " = " + currentUser.getTenantId());
        }
        if(params != null) {
            for (String param : params) {
                sql.append(" ").append(param);
            }
        }
        return sql.toString();
    }
}
