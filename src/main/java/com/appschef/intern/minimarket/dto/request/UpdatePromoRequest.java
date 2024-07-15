package com.appschef.intern.minimarket.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePromoRequest {
    @NotBlank(message = "nama_promo harus diisi")
    @Size(max = 100, message = "nama_promo tidak boleh lebih dari 100 karakter")
    @JsonProperty("nama_promo")
    private String namaPromo;

    @NotBlank(message = "kode_produk harus diisi")
    @Size(max = 13, message = "kode_produk tidak boleh lebih dari 13 karakter")
    @JsonProperty("kode_produk")
    private String kodeProduk;

    @NotBlank(message = "jenis harus diisi")
    @JsonProperty("jenis")
    private String jenis;

    @NotNull(message = "nilai harus diisi")
    @PositiveOrZero(message = "nilai harus lebih besar atau sama dengan 0")
    @JsonProperty("nilai")
    private BigDecimal nilai;

    @NotBlank(message = "tanggal_mulai harus diisi")
    @JsonProperty("tanggal_mulai")
    private String tanggalMulai;

    @NotBlank(message = "tanggal_berakhir harus diisi")
    @JsonProperty("tanggal_berakhir")
    private String tanggalBerakhir;
}
