package com.appschef.intern.minimarket.service;

import com.appschef.intern.minimarket.dto.request.PembelianRequest;
import com.appschef.intern.minimarket.dto.request.ProdukPembelianRequest;
import com.appschef.intern.minimarket.dto.request.TopMemberRequest;
import com.appschef.intern.minimarket.dto.response.*;
import com.appschef.intern.minimarket.entity.*;
import com.appschef.intern.minimarket.enumMessage.JenisPromo;
import com.appschef.intern.minimarket.enumMessage.MemberErrorMessage;
import com.appschef.intern.minimarket.enumMessage.ProdukErrorMessage;
import com.appschef.intern.minimarket.enumMessage.PromoErrorMessage;
import com.appschef.intern.minimarket.mapper.MemberMapper;
import com.appschef.intern.minimarket.mapper.ProdukMapper;
import com.appschef.intern.minimarket.mapper.PromoPembelianMapper;
import com.appschef.intern.minimarket.projection.PenjualanPerHariProjection;
import com.appschef.intern.minimarket.projection.TopMemberProjection;
import com.appschef.intern.minimarket.projection.TopProdukProjection;
import com.appschef.intern.minimarket.repository.*;
import com.appschef.intern.minimarket.util.ApplicationProperties;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PembelianService {
    @Autowired
    private PembelianRepository pembelianRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProdukRepository produkRepository;

    @Autowired
    private PromoRepository promoRepository;

    @Autowired
    private DetailPembelianRepository detailPembelianRepository;

    @Autowired
    private PromoPembelianRepository promoPembelianRepository;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Transactional
    public PembelianResponse addPembelian(PembelianRequest pembelianRequest) {
        Member member = null;
        if(pembelianRequest.getNomorMember() != null){
            member = memberRepository.findMemberByNomorMemberAndIsDeletedFalse(pembelianRequest.getNomorMember())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, MemberErrorMessage.NOT_FOUND.getMessage()));
        }

        Pembelian pembelian = new Pembelian(null, null, new Date(), member, null);

        // Mendapatkan tanggal saat ini
        LocalDate currentDate = LocalDate.now();
        String format = currentDate.format(DateTimeFormatter.ofPattern("ddMMyy"));

        String nomorStrukTerakhir = pembelianRepository.getNomorStrukTerakhir((format + "%"));
        if(nomorStrukTerakhir == null){
            pembelian.setNomorStruk(format + "0001");
        }else{
            String tigaAngkaTerakhir = nomorStrukTerakhir.substring(nomorStrukTerakhir.length() - 4);
            Long nomorStrukBaruLong = Long.parseLong(tigaAngkaTerakhir);
            nomorStrukBaruLong++;
            String newMemberNumber = format + String.format("%04d", nomorStrukBaruLong);
            pembelian.setNomorStruk(newMemberNumber);
        }

        pembelianRepository.saveAndFlush(pembelian);

        List<ProdukPembelianResponse> listProdukPembelian = new ArrayList<>();
        BigDecimal totalHargaKeseluruhan = BigDecimal.ZERO;

        //iterasi listProdukPembelian
        for(ProdukPembelianRequest produkPembelianRequest : pembelianRequest.getListProdukPembelian()){
            Produk produk = produkRepository.findByKodeProdukAndIsDeletedFalse(produkPembelianRequest.getKodeProduk())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ProdukErrorMessage.NOT_FOUND.getMessage()));

            //cek promo produk
            List<Promo> listPromo = promoRepository.findValidPromoByProduk(pembelian.getTanggal(), produk);

            BigDecimal hargaProdukFinal = produk.getHarga();
            List<PromoPembelian> listPromoPembelian = new ArrayList<>();
            //menghitung harga produk setelah promo
            for(Promo promo : listPromo){
                if(Objects.equals(promo.getJenis(), JenisPromo.PERSEN.getJenis())){
                    hargaProdukFinal = hargaProdukFinal.subtract(hargaProdukFinal.multiply(promo.getNilai()));
                }else if (Objects.equals(promo.getJenis(), JenisPromo.FLAT_NOMINAL.getJenis())){
                    hargaProdukFinal = hargaProdukFinal.subtract(promo.getNilai());
                }

                listPromoPembelian.add(PromoPembelianMapper.mapToPromoPembelian(promo));
            }

            hargaProdukFinal = hargaProdukFinal.setScale(2, RoundingMode.HALF_UP);

            if (hargaProdukFinal.compareTo(BigDecimal.ZERO) < 0) {
                hargaProdukFinal = BigDecimal.ZERO;
            }

            DetailPembelian detailPembelian = new DetailPembelian(
                    null,
                    produkPembelianRequest.getJumlahProduk(),
                    produk.getKodeProduk(),
                    produk.getNamaProduk(),
                    produk.getHarga(),
                    hargaProdukFinal,
                    pembelian,
                    produk,
                    listPromoPembelian
            );

            DetailPembelian savedDetailPembelian = detailPembelianRepository.save(detailPembelian);

            for (PromoPembelian promoPembelian : listPromoPembelian){
                promoPembelian.setDetailPembelian(savedDetailPembelian);
                promoPembelianRepository.save(promoPembelian);
            }

            BigDecimal jumlahProdukBigDecimal = BigDecimal.valueOf(produkPembelianRequest.getJumlahProduk());
            BigDecimal totalHargaProduk = hargaProdukFinal.multiply(jumlahProdukBigDecimal);

            //akumulasi potongan harga
            BigDecimal potonganHarga = produk.getHarga().subtract(hargaProdukFinal);

            //akumulasi harga keseluruhan
            totalHargaKeseluruhan = totalHargaKeseluruhan.add(totalHargaProduk);

            listProdukPembelian.add(new ProdukPembelianResponse(
                    produk.getKodeProduk(),
                    produk.getNamaProduk(),
                    hargaProdukFinal,
                    potonganHarga,
                    listPromoPembelian.stream().map(PromoPembelianMapper::mapToPromoPembelianResponse).toList(),
                    produkPembelianRequest.getJumlahProduk(),
                    totalHargaProduk
            ));
        }

        PembelianResponse pembelianResponse = new PembelianResponse(
                null,
                pembelian.getNomorStruk(),
                listProdukPembelian,
                totalHargaKeseluruhan
        );

        if (member != null){
            //tambah poin member
            member.setPoin(member.getPoin() + applicationProperties.getPoinPerTransaksi());
            memberRepository.save(member);

            pembelianResponse.setNomorMember(member.getNomorMember());
        }

        return pembelianResponse;
    }

    public PenjualanPerHariResponse getLaporanPenjualanPerHari(Date tanggal){
        PenjualanPerHariProjection penjualanPerHariProjection = pembelianRepository.getLaporanPerHari(tanggal);

        PenjualanPerHariResponse penjualanPerhariResponse;
        if(penjualanPerHariProjection == null){
            penjualanPerhariResponse = new PenjualanPerHariResponse(
                    tanggal,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO
            );
        }else {
            BigDecimal selisih = penjualanPerHariProjection.getTotalPenjualanKotor().subtract(penjualanPerHariProjection.getTotalPenjualanBersih());
            penjualanPerhariResponse = new PenjualanPerHariResponse(
                    tanggal,
                    penjualanPerHariProjection.getTotalPenjualanKotor(),
                    selisih,
                    penjualanPerHariProjection.getTotalPenjualanBersih()
            );
        }

        return penjualanPerhariResponse;
    }

    public List<TopMemberResponse> getTopMemberByTotalNominalPembelian(){
        List<TopMemberProjection> listTopMemberProjection = memberRepository.getTopMember();

        return listTopMemberProjection.stream().map(MemberMapper::mapToTopMemberResponse).toList();
    }

    public TopPembelianMemberResponse getTopPembelianMember(String nomorMember){
        Member member = memberRepository.findMemberByNomorMemberAndIsDeletedFalse(nomorMember)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, MemberErrorMessage.NOT_FOUND.getMessage()));

        List<TopProdukProjection> listTopProdukProjection = produkRepository.getTopProdukMember(nomorMember);

        List<TopProdukResponse> listTopProdukResponse = setNamaProdukToTopProdukResponse(listTopProdukProjection);

        return new TopPembelianMemberResponse(
                member.getNomorMember(),
                member.getNamaLengkap(),
                listTopProdukResponse
        );
    }

    public List<TopProdukResponse> getTopProduk(Date tanggalAwal, Date tanggalAkhir){
        List<TopProdukProjection> listTopProdukProjection = produkRepository.getTopProduk(tanggalAwal, tanggalAkhir);

        return setNamaProdukToTopProdukResponse(listTopProdukProjection);
    }

    private List<TopProdukResponse> setNamaProdukToTopProdukResponse(List<TopProdukProjection> listTopProdukResponse){
        List<TopProdukResponse> listProdukResponse = new ArrayList<>();
        for (TopProdukProjection topProdukProjection : listTopProdukResponse){
            String namaProduk = produkRepository.findNamaProdukByKodeProduk(topProdukProjection.getKodeProduk());
            listProdukResponse.add(new TopProdukResponse(
                    topProdukProjection.getKodeProduk(),
                    namaProduk,
                    topProdukProjection.getJumlahPembelian())
            );
        }

        return listProdukResponse;
    }

    public LaporanPromoResponse getLaporanPromo (String kodePromo){
        Promo promo = promoRepository.findByKodePromo(kodePromo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, PromoErrorMessage.NOT_FOUND.getMessage()));

        Long jumlahPemakaian = promoRepository.getJumlahPemakaianPromo(kodePromo);

        List<TopMemberProjection> listMemberPemakai = promoRepository.topMemberPemakaiPromo(kodePromo);

        return new LaporanPromoResponse(
                promo.getKodePromo(),
                promo.getNamaPromo(),
                promo.getProduk().getKodeProduk(),
                promo.getProduk().getNamaProduk(),
                jumlahPemakaian,
                listMemberPemakai.stream().map(MemberMapper::mapToTopMemberResponse).toList()
        );
    }

}
