package com.appschef.intern.minimarket.enumMessage;

import lombok.Getter;

@Getter
public enum ProdukErrorMessage {
    NOT_FOUND("Produk tidak ditemukan"),
    EXIST("Produk sudah terdaftar sebelumnya"),
    GAGAL_HAPUS("Produk gagal dihapus");

    private final String message;

    ProdukErrorMessage(String message) {
        this.message = message;
    }
}
