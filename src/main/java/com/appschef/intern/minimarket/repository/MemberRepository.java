package com.appschef.intern.minimarket.repository;

import com.appschef.intern.minimarket.entity.Member;
import com.appschef.intern.minimarket.projection.TopMemberProjection;
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

public interface MemberRepository extends JpaRepository<Member, Long> {
    //cari nomor member terakhir yang sesuai format
    @Query(value = "SELECT nomor_member FROM member " +
            "WHERE nomor_member LIKE :formatNomorMember " +
            "ORDER BY nomor_member DESC " +
            "LIMIT 1", nativeQuery = true)
    String getNomorMemberTerakhir(@Param("formatNomorMember") String formatNomorMember);

    //get member detail
    Optional<Member> findMemberByNomorMemberAndIsDeletedFalse(@Param("nomorMember") String nomorMember);

    //get all member
    Page<Member> findByIsDeletedFalse(Pageable pageable);

    //find member / cek member
    @Query("SELECT m.id FROM Member m WHERE m.nomorMember = :nomorMember AND m.isDeleted = false")
    Optional<Long> findIdByNomorMemberAndIsDeletedFalse(@Param("nomorMember") String nomorMember);

    //soft is_deleted member
    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.isDeleted = true, m.updatedAt = :updatedAt WHERE m.id = :id")
    int softDeleteById(@Param("id") Long id, @Param("updatedAt") Date updatedAt);

    @Query(value = "SELECT a.nomor_member as nomorMember, a.nama_lengkap as namaLengkap, " +
            "COALESCE(SUM(c.harga_jual_produk * c.jumlah_produk), 0) AS jumlahPembelian " +
            "FROM member a " +
            "LEFT JOIN pembelian b ON b.id_member = a.id " +
            "LEFT JOIN detail_pembelian c ON c.id_pembelian = b.id " +
            "GROUP BY a.nomor_member, a.nama_lengkap " +
            "ORDER BY jumlahPembelian DESC " +
            "LIMIT 3 ", nativeQuery = true)
    List<TopMemberProjection> getTopMember();
}
