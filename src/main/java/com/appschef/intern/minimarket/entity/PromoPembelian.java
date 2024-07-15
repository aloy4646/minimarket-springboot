package com.appschef.intern.minimarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promo_pembelian")
public class PromoPembelian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_promo", nullable = false)
    private Long idPromo;

    @Column(name = "kode_promo", nullable = false)
    @Size(max = 7)
    private String kodePromo;

    @Column(name = "nama_promo", nullable = false)
    @Size(max = 100)
    private String namaPromo;

    @Column(name = "jenis", nullable = false)
    private String jenis;

    @Column(name = "nilai", nullable = false)
    private BigDecimal nilai;

    @ManyToOne
    @JoinColumn(name = "id_detail_pembelian", referencedColumnName = "id")
    private DetailPembelian detailPembelian;
}
