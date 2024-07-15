package com.appschef.intern.minimarket.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdukPembelianRequest {
    @NotBlank(message = "kode_produk harus diisi")
    @Size(max = 13, message = "kode_produk tidak boleh lebih dari 13 karakter")
    @JsonProperty("kode_produk")
    private String kodeProduk;

    @NotNull(message = "jumlah_produk harus diisi")
    @Positive(message = "jumlah_produk harus lebih besar dari 0")
    @JsonProperty("jumlah_produk")
    private Integer jumlahProduk;
}
