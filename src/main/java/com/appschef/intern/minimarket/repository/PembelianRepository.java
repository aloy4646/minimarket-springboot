package com.appschef.intern.minimarket.repository;

import com.appschef.intern.minimarket.entity.Pembelian;
import com.appschef.intern.minimarket.projection.PenjualanPerHariProjection;
import com.appschef.intern.minimarket.projection.TopMemberProjection;
import com.appschef.intern.minimarket.projection.TopProdukProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PembelianRepository extends JpaRepository<Pembelian, Long> {
    //cari nomor struk terakhir yang sesuai format
    @Query(value = "SELECT nomor_struk FROM pembelian " +
            "WHERE nomor_struk LIKE :formatNomorStruk " +
            "ORDER BY nomor_struk DESC " +
            "LIMIT 1", nativeQuery = true)
    String getNomorStrukTerakhir(@Param("formatNomorStruk") String formatNomorStruk);


    @Query(value = "SELECT DATE(a.tanggal) AS tanggal, " +
            "SUM(b.harga_produk * b.jumlah_produk) AS totalPenjualanKotor, " +
            "SUM(b.harga_jual_produk * b.jumlah_produk) AS totalPenjualanBersih " +
            "FROM pembelian a " +
            "JOIN detail_pembelian b ON b.id_pembelian = a.id " +
            "WHERE DATE(a.tanggal) = :tanggal " +
            "GROUP BY DATE(a.tanggal)", nativeQuery = true)
    PenjualanPerHariProjection getLaporanPerHari(@Param("tanggal") Date tanggal);
}
