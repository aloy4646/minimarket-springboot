package com.appschef.intern.minimarket.repository;

import com.appschef.intern.minimarket.entity.Produk;
import com.appschef.intern.minimarket.projection.TopProdukProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProdukRepository extends JpaRepository<Produk, Long> {
    //mengecek kodeProduk
    boolean existsByKodeProduk(String kodeProduk);

    //get produk detail
    Optional<Produk> findByKodeProdukAndIsDeletedFalse(@Param("kodeProduk") String kodeProduk);

    //get all produk
    Page<Produk> findByIsDeletedFalse(Pageable pageable);

    //find produk / cek produk
    @Query("SELECT p.id FROM Produk p WHERE p.kodeProduk = :kodeProduk AND p.isDeleted = false")
    Optional<Long> findIdByKodeProdukAndIsDeletedFalse(@Param("kodeProduk") String kodeProduk);

    @Modifying
    @Transactional
    @Query("UPDATE Produk p SET p.isDeleted = true, p.updatedAt = CURRENT_TIMESTAMP WHERE p.id = :id")
    int softDeleteById(@Param("id") Long id);

    @Query(value = "SELECT a.kode_produk as kodeProduk, " +
            "SUM(b.jumlah_produk) AS jumlahPembelian " +
            "FROM produk a " +
            "JOIN detail_pembelian b ON b.id_produk = a.id " +
            "JOIN pembelian c ON c.id = b.id_pembelian " +
            "JOIN member d ON d.id = c.id_member " +
            "WHERE d.nomor_member = :nomorMember " +
            "GROUP BY a.kode_produk " +
            "ORDER BY jumlahPembelian DESC " +
            "LIMIT 3;", nativeQuery = true)
    List<TopProdukProjection> getTopProdukMember(@Param("nomorMember") String nomorMember);

    @Query(value = "SELECT a.kode_produk, " +
            "COALESCE(SUM(b.jumlah_produk), 0) AS jumlahPembelian " +
            "FROM produk a " +
            "LEFT JOIN detail_pembelian b ON b.id_produk = a.id " +
            "LEFT JOIN pembelian c ON c.id = b.id_pembelian " +
            "AND DATE(c.tanggal) BETWEEN :tanggalAwal AND :tanggalAkhir " +
            "GROUP BY a.kode_produk " +
            "ORDER BY jumlahPembelian DESC " +
            "LIMIT 3", nativeQuery = true)
    List<TopProdukProjection> getTopProduk(@Param("tanggalAwal") Date tanggalAwal , @Param("tanggalAkhir") Date tanggalAkhir);

    @Query("SELECT p.namaProduk FROM Produk p WHERE p.kodeProduk = :kodeProduk")
    String findNamaProdukByKodeProduk(String kodeProduk);
}
