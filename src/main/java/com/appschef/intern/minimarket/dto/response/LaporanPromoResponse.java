package com.appschef.intern.minimarket.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaporanPromoResponse {
    @JsonProperty("kode_promo")
    private String kodePromo;

    @JsonProperty("nama_promo")
    private String namaPromo;

    @JsonProperty("kode_produk")
    private String kodeProduk;

    @JsonProperty("nama_produk")
    private String namaProduk;

    @JsonProperty("jumlah_pemakaian")
    private Long jumlahPemakaian;

    @JsonProperty("top_member_pemakai")
    private List<TopMemberResponse> listTopMember;
}
