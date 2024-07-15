package com.appschef.intern.minimarket.projection;

import java.math.BigDecimal;

public interface PenjualanPerHariProjection {
    BigDecimal getTotalPenjualanKotor();
    BigDecimal getTotalPenjualanBersih();
}
