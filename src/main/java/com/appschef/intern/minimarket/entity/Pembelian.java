package com.appschef.intern.minimarket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
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
@Table(name = "pembelian")
public class Pembelian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomor_struk", nullable = false, unique = true)
    @Size(max = 10)
    private String nomorStruk;

    @Column(name = "tanggal", columnDefinition = "TIMESTAMP with time zone")
    private Date tanggal;

    @ManyToOne
    @JoinColumn(name = "id_member", referencedColumnName = "id")
    private Member member;

    @OneToMany(mappedBy = "pembelian")
    private List<DetailPembelian> listDetailPembelian;

}
