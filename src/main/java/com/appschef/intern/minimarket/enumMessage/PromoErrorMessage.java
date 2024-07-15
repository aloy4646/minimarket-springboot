package com.appschef.intern.minimarket.enumMessage;

import lombok.Getter;

@Getter
public enum PromoErrorMessage {
    NOT_FOUND("Promo tidak ditemukan"),
    GAGAL_HAPUS("Promo gagal dihapus"),
    EXIST("Promo sudah terdaftar sebelumnya"),
    JENIS_INVALID("Jenis promo tidak sesuai"),
    NILAI_INVALID("Nilai promo tidak sesuai");

    private final String message;

    PromoErrorMessage(String message) {
        this.message = message;
    }
}
