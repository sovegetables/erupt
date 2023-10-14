package xyz.erupt.upms.helper;

import org.springframework.stereotype.Service;
import xyz.erupt.annotation.fun.DataProxy;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.model.EruptUserVo;
import xyz.erupt.upms.model.base.HyperModel;
import xyz.erupt.upms.service.EruptUserService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author YuePeng
 * date 2020-08-04
 */
@Service
public class TenantDataProxy implements DataProxy<TenantDataProxy.TenantAction> {

    public interface TenantAction{
        Long getTenantId();
        void setTenantId(Long tenantId);
    }

    @Resource
    private EruptUserService eruptUserService;

    @Override
    public void beforeAdd(TenantDataProxy.TenantAction hyperModel) {
        EruptUser currentUser = eruptUserService.getCurrentEruptUser();
        Long tenantId = currentUser.getTenantId();
        hyperModel.setTenantId(tenantId);
    }

    @Override
    public void beforeUpdate(TenantDataProxy.TenantAction hyperModel) {
        EruptUser currentUser = eruptUserService.getCurrentEruptUser();
        Long tenantId = currentUser.getTenantId();
        hyperModel.setTenantId(tenantId);
    }
}
