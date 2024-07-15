package com.appschef.intern.minimarket.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopPembelianMemberResponse {
    @JsonProperty("nomor_member")
    private String nomorMember;

    @JsonProperty("nama_lengkap")
    private String namaLengkap;

    @JsonProperty("list_produk")
    private List<TopProdukResponse> listProduk;
}
