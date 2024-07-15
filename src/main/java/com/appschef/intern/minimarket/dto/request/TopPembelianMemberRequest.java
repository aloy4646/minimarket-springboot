package com.appschef.intern.minimarket.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopPembelianMemberRequest {
    @Size(max = 7, message = "nomor_member tidak boleh lebih dari 13 karakter")
    @JsonProperty("nomor_member")
    private String nomorMember;
}
