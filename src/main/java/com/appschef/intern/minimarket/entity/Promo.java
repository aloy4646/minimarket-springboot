package com.appschef.intern.minimarket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promo")
public class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kode_promo", nullable = false, unique = true)
    @Size(max = 7)
    private String kodePromo;

    @Column(name = "nama_promo", nullable = false)
    @Size(max = 100)
    private String namaPromo;

    @Column(name = "jenis", nullable = false)
    private String jenis;

    @Column(name = "nilai", nullable = false)
    private BigDecimal nilai;

    @Column(name = "tanggal_mulai", columnDefinition = "TIMESTAMP with time zone")
    private Date tanggalMulai;

    @Column(name = "tanggal_berakhir", columnDefinition = "TIMESTAMP with time zone")
    private Date tanggalBerakhir;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP  with time zone", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "id_produk", referencedColumnName = "id")
    private Produk produk;
}
