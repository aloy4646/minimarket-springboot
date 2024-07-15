package com.appschef.intern.minimarket.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProdukRequest {
    @NotBlank(message = "kode_produk harus diisi")
    @Size(max = 13, message = "kode_produk tidak boleh lebih dari 13 karakter")
    @JsonProperty("kode_produk")
    private String kodeProduk;

    @NotBlank(message = "nama_produk harus diisi")
    @Size(max = 100, message = "nama_produk tidak boleh lebih dari 100 karakter")
    @JsonProperty("nama_produk")
    private String namaProduk;

    @NotNull(message = "harga harus diisi")
    @PositiveOrZero(message = "harga harus lebih besar atau sama dengan 0")
    @JsonProperty("harga")
    private BigDecimal harga;
}
