package com.appschef.intern.minimarket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produk")
public class Produk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kode_produk", nullable = false, unique = true)
    @Size(max = 13)
    private String kodeProduk;

    @Column(name = "nama_produk", nullable = false)
    @Size(max = 100)
    private String namaProduk;

    @Column(name = "harga", columnDefinition = "DECIMAL", nullable = false)
    private BigDecimal harga;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP  with time zone", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP  with time zone")
    private Date updatedAt;

    @OneToMany(mappedBy = "produk")
    private List<DetailPembelian> listDetailPembelian;

    @OneToMany(mappedBy = "produk")
    private List<Promo> listPromo;

}
