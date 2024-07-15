package com.appschef.intern.minimarket.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequest {
    @NotBlank(message = "nama_lengkap harus diisi")
    @Size(max = 100, message = "nama_lengkap tidak boleh lebih dari 100 karakter")
    @JsonProperty("nama_lengkap")
    private String namaLengkap;

    @NotBlank(message = "email harus diisi")
    @Size(max = 360, message = "email tidak boleh lebih dari 360 karakter")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Format email tidak valid")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "nomor_telepon harus diisi")
    @Size(max = 13, message = "nomor_telepon tidak boleh lebih dari 13 karakter")
    @Pattern(regexp = "^(\\+62|62|0)[0-9]{9,13}$", message = "Format nomor telepon tidak valid")
    @JsonProperty("nomor_telepon")
    private String nomorTelepon;

    @NotBlank(message = "alamat harus diisi")
    @JsonProperty("alamat")
    private String alamat;
}
