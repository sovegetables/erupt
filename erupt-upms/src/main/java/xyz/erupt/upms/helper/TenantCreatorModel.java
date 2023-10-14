package xyz.erupt.upms.helper;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.upms.helper.HyperModelCreatorVo;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class TenantCreatorModel extends HyperModelUpdateVo implements TenantDataProxy.TenantAction{
    @EruptField
    private Long tenantId;
}
