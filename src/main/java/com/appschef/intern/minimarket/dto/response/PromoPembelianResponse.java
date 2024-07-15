package com.appschef.intern.minimarket.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoPembelianResponse {
    @JsonProperty("kode_promo")
    private String kodePromo;

    @JsonProperty("nama_promo")
    private String namaPromo;

    @JsonProperty("jenis")
    private String jenis;

    @JsonProperty("nilai")
    private BigDecimal nilai;
}
