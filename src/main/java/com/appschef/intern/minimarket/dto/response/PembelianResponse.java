package com.appschef.intern.minimarket.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PembelianResponse {
    @JsonProperty("nomor_member")
    private String nomorMember;

    @JsonProperty("nomor_struk")
    private String nomorStruk;

    @JsonProperty("list_produk")
    private List<ProdukPembelianResponse> listProdukPembelian;

    @JsonProperty("total_harga_keseluruhan")
    private BigDecimal totalHargaKeseluruhan;
}
