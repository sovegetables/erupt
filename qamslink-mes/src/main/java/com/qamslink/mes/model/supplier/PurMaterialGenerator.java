package com.qamslink.mes.model.supplier;

import lombok.Getter;
import lombok.Setter;
import xyz.erupt.annotation.Erupt;
import xyz.erupt.annotation.sub_erupt.Power;
import xyz.erupt.upms.helper.HyperModelVo;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name ="mes_material_batch_barcode")
@Erupt(name = "供应商生成物料条码",
        power = @Power(add = false, delete = false, edit = false)
)
public class PurMaterialGenerator extends HyperModelVo {

}
