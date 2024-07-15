package com.appschef.intern.minimarket.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdukPembelianResponse {
    @JsonProperty("kode_produk")
    private String kodeProduk;

    @JsonProperty("nama_produk")
    private String namaProduk;

    @JsonProperty("harga_produk")
    private BigDecimal hargaProduk;

    @JsonProperty("potongan_harga")
    private BigDecimal potonganHarga;

    @JsonProperty("list_promo")
    private List<PromoPembelianResponse> listPromo;

    @JsonProperty("jumlah_produk")
    private Integer jumlahProduk;

    @JsonProperty("total_harga")
    private BigDecimal totalHarga;
}
