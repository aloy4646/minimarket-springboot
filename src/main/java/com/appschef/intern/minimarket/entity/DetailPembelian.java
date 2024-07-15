package com.appschef.intern.minimarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detail_pembelian")
public class DetailPembelian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jumlah_produk", nullable = false)
    private Integer jumlahProduk;

    @Column(name = "kode_produk", nullable = false)
    private String kodeProduk;

    @Column(name = "nama_produk", nullable = false)
    private String namaProduk;

    @Column(name = "harga_produk", columnDefinition = "DECIMAL" , nullable = false)
    private BigDecimal hargaProduk;

    @Column(name = "harga_jual_produk", columnDefinition = "DECIMAL", nullable = false)
    private BigDecimal hargaJualProduk;

    @ManyToOne
    @JoinColumn(name = "id_pembelian", referencedColumnName = "id")
    private Pembelian pembelian;

    @ManyToOne
    @JoinColumn(name = "id_produk", referencedColumnName = "id")
    private Produk produk;

    @OneToMany(mappedBy = "detailPembelian")
    private List<PromoPembelian> listPromoPembelian;
}
