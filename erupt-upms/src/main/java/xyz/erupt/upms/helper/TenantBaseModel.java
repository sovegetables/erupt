package xyz.erupt.upms.helper;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.jpa.model.BaseModel;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class TenantBaseModel extends BaseModel implements TenantDataProxy.TenantAction{
    @EruptField
    private Long tenantId;
}
