package com.appschef.intern.minimarket.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PembelianRequest {
    @Size(max = 7, message = "nomor_member tidak boleh lebih dari 13 karakter")
    @JsonProperty("nomor_member")
    private String nomorMember;

    @NotNull(message = "list_produk harus diisi")
    @JsonProperty("list_produk")
    @Valid
    private List<ProdukPembelianRequest> listProdukPembelian;

}
