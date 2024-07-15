package com.appschef.intern.minimarket.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailPromoResponse {
    @JsonProperty("kode_promo")
    private String kodePromo;

    @JsonProperty("nama_promo")
    private String namaPromo;

    @JsonProperty("kode_produk")
    private String kodeProduk;

    @JsonProperty("nama_produk")
    private String namaProduk;

    @JsonProperty("jenis")
    private String jenis;

    @JsonProperty("nilai")
    private BigDecimal nilai;

    @JsonProperty("tanggal_mulai")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalMulai;

    @JsonProperty("tanggal_berakhir")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggalBerakhir;

}
