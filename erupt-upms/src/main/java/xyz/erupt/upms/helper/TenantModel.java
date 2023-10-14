package xyz.erupt.upms.helper;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.upms.model.base.HyperModel;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class TenantModel extends HyperModel implements TenantDataProxy.TenantAction{
    @EruptField
    private Long tenantId;
}
