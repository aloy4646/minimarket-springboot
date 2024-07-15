package com.appschef.intern.minimarket.mapper;

import com.appschef.intern.minimarket.dto.request.AddProdukRequest;
import com.appschef.intern.minimarket.dto.response.DetailProdukResponse;
import com.appschef.intern.minimarket.dto.response.TopProdukResponse;
import com.appschef.intern.minimarket.entity.Produk;
import com.appschef.intern.minimarket.projection.TopProdukProjection;

import java.util.Date;

public class ProdukMapper {
    public static Produk mapToProduk(AddProdukRequest addProdukRequest){

        return new Produk(
                null,
                addProdukRequest.getKodeProduk(),
                addProdukRequest.getNamaProduk(),
                addProdukRequest.getHarga(),
                false,
                new Date(),
                null,
                null,
                null
        );
    }

    public static DetailProdukResponse mapToDetailProdukResponse(Produk produk){

        return new DetailProdukResponse(
                produk.getKodeProduk(),
                produk.getNamaProduk(),
                produk.getHarga()
        );
    }
}
