package com.appschef.intern.minimarket.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopProdukRequest {
    @NotNull(message = "tanggal_awal harus diisi")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("tanggal_awal")
    private Date tanggalAwal;

    @NotNull(message = "tanggal_akhir harus diisi")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("tanggal_akhir")
    private Date tanggalAkhir;

}
