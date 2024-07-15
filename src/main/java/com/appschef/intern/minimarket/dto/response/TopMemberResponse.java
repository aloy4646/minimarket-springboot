package com.appschef.intern.minimarket.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopMemberResponse {
    @JsonProperty("nama_lengkap")
    private String namaLengkap;

    @JsonProperty("nomor_member")
    private String nomorMember;

    @JsonProperty("jumlah_pembelian")
    private BigDecimal jumlahPembelian;
}
