package com.appschef.intern.minimarket.enumMessage;

import lombok.Getter;

@Getter
public enum JenisPromo {
    PERSEN("PERSEN"),
    FLAT_NOMINAL("FLAT NOMINAL");

    private final String jenis;

    JenisPromo(String jenis) {
        this.jenis = jenis;
    }
}
