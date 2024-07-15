package com.appschef.intern.minimarket.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailMemberResponse {
    @JsonProperty("nama_lengkap")
    private String namaLengkap;

    @JsonProperty("nomor_member")
    private String nomorMember;

    @JsonProperty("email")
    private String email;

    @JsonProperty("nomor_telepon")
    private String nomorTelepon;

    @JsonProperty("alamat")
    private String alamat;

    @JsonProperty("poin")
    private Long poin;
}
