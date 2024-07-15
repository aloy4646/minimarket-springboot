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

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomor_member", nullable = false, unique = true)
    @Size(max = 7)
    private String nomorMember;

    @Column(name = "nama_lengkap", nullable = false)
    @Size(max = 100)
    private String namaLengkap;

    @Column(name = "email", nullable = false)
    @Size(max = 360)
    private String email;

    @Column(name = "nomor_telepon", nullable = false)
    @Size(max = 13)
    private String nomorTelepon;

    @Column(name = "alamat", columnDefinition = "TEXT", nullable = false)
    private String alamat;

    @Column(name = "poin")
    private Long poin;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP  with time zone", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP  with time zone")
    private Date updatedAt;

    @OneToMany(mappedBy = "member")
    private List<Pembelian> listPembelian;
}
