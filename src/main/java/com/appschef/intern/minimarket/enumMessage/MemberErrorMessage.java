package com.appschef.intern.minimarket.enumMessage;

import lombok.Getter;

@Getter
public enum MemberErrorMessage {
    NOT_FOUND("Member tidak ditemukan"),
    GAGAL_HAPUS("Member gagal dihapus");

    private final String message;

    MemberErrorMessage(String message) {
        this.message = message;
    }

}
