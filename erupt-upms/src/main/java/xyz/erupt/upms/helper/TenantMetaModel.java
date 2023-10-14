package xyz.erupt.upms.helper;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.EruptField;
import xyz.erupt.jpa.model.MetaModel;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class TenantMetaModel extends MetaModel implements TenantDataProxy.TenantAction{
    @EruptField
    private Long tenantId;
}
