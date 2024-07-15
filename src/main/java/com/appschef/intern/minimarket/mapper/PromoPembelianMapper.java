package com.appschef.intern.minimarket.mapper;

import com.appschef.intern.minimarket.dto.response.PromoPembelianResponse;
import com.appschef.intern.minimarket.entity.Promo;
import com.appschef.intern.minimarket.entity.PromoPembelian;

public class PromoPembelianMapper {
    public static PromoPembelian mapToPromoPembelian(Promo promo){

        return new PromoPembelian(
                null,
                promo.getId(),
                promo.getKodePromo(),
                promo.getNamaPromo(),
                promo.getJenis(),
                promo.getNilai(),
                null
        );
    }

    public static PromoPembelianResponse mapToPromoPembelianResponse(PromoPembelian promoPembelian){

        return new PromoPembelianResponse(
                promoPembelian.getKodePromo(),
                promoPembelian.getNamaPromo(),
                promoPembelian.getJenis(),
                promoPembelian.getNilai()
        );
    }
}
