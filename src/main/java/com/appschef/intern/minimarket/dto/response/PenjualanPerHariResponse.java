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
public class PenjualanPerHariResponse {
    @JsonProperty("tanggal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggal;

    @JsonProperty("total_penjualan_kotor")
    private BigDecimal totalPenjualanKotor;

    @JsonProperty("total_potongan_harga")
    private BigDecimal totalPotonganHarga;

    @JsonProperty("total_penjualan_bersih")
    private BigDecimal totalPenjualanBersih;
}
