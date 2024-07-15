package com.appschef.intern.minimarket.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailProdukResponse {
    @JsonProperty("kode_produk")
    private String kodeProduk;

    @JsonProperty("nama_produk")
    private String namaProduk;

    @JsonProperty("harga")
    private BigDecimal harga;
}
