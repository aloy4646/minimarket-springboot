package com.appschef.intern.minimarket.repository;

import com.appschef.intern.minimarket.entity.Produk;
import com.appschef.intern.minimarket.entity.Promo;
import com.appschef.intern.minimarket.projection.TopMemberProjection;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PromoRepository extends JpaRepository<Promo, Long> {
    //mengecek kodePromo
    boolean existsByKodePromo(String kodePromo);

    //get promo detail
    Optional<Promo> findByKodePromo(@Param("kodePromo") String kodePromo);

    //get all promo
    Page<Promo> findAll(Pageable pageable);

    //find promo / cek promo
    Optional<Long> findIdByKodePromo(@Param("kodePromo") String kodePromo);

    @Query("SELECT promo " +
            "FROM Promo promo JOIN promo.produk produk " +
            "WHERE :date BETWEEN promo.tanggalMulai AND promo.tanggalBerakhir " +
            "AND produk = :produk ")
    List<Promo> findValidPromoByProduk(@Param("date") Date date, @Param("produk") Produk produk);

    @Query(value = "SELECT COALESCE(COUNT(c.id),0) AS jumlahPembelian " +
            "FROM promo_pembelian a " +
            "JOIN detail_pembelian b ON a.id_detail_pembelian = b.id " +
            "JOIN pembelian c ON b.id_pembelian = c.id " +
            "WHERE a.kode_promo = :kodePromo ", nativeQuery = true)
    Long getJumlahPemakaianPromo (@Param("kodePromo") String kodePromo);

    @Query(value = "SELECT d.nomor_member, d.nama_lengkap, " +
            "COUNT(c.id) AS jumlahPembelian " +
            "FROM promo_pembelian a " +
            "JOIN detail_pembelian b ON a.id_detail_pembelian = b.id " +
            "JOIN pembelian c ON b.id_pembelian = c.id " +
            "JOIN member d ON c.id_member = d.id " +
            "WHERE a.kode_promo = :kodePromo AND c.id_member IS NOT NULL " +
            "GROUP BY d.nomor_member, d.nama_lengkap", nativeQuery = true)
    List<TopMemberProjection> topMemberPemakaiPromo(@Param("kodePromo") String kodePromo);
}
