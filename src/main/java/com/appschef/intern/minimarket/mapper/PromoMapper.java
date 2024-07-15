package com.appschef.intern.minimarket.mapper;

import com.appschef.intern.minimarket.dto.request.AddPromoRequest;
import com.appschef.intern.minimarket.dto.response.DetailPromoResponse;
import com.appschef.intern.minimarket.entity.Promo;

import java.util.Date;

public class PromoMapper {
    public static Promo mapToPromo(AddPromoRequest addPromoRequest){

        return new Promo(
                null,
                addPromoRequest.getKodePromo(),
                addPromoRequest.getNamaPromo(),
                addPromoRequest.getJenis(),
                addPromoRequest.getNilai(),
                null,
                null,
                new Date(),
                null
        );
    }

    public static DetailPromoResponse mapToDetailPromoResponse(Promo promo){

        return new DetailPromoResponse(
                promo.getKodePromo(),
                promo.getNamaPromo(),
                promo.getProduk().getKodeProduk(),
                promo.getProduk().getNamaProduk(),
                promo.getJenis(),
                promo.getNilai(),
                promo.getTanggalMulai(),
                promo.getTanggalBerakhir()
        );
    }
}
