package com.appschef.intern.minimarket.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopMemberRequest {
    @NotBlank(message = "nomor_member harus diisi")
    @Size(max = 7, message = "nomor_member tidak boleh lebih dari 7 karakter")
    @JsonProperty("nomor_member")
    private String nomorMember;
}
