package com.appschef.intern.minimarket.service;

import com.appschef.intern.minimarket.dto.request.AddPromoRequest;
import com.appschef.intern.minimarket.dto.request.UpdatePromoRequest;
import com.appschef.intern.minimarket.dto.response.DetailPromoResponse;
import com.appschef.intern.minimarket.entity.Produk;
import com.appschef.intern.minimarket.entity.Promo;
import com.appschef.intern.minimarket.enumMessage.JenisPromo;
import com.appschef.intern.minimarket.enumMessage.ProdukErrorMessage;
import com.appschef.intern.minimarket.enumMessage.PromoErrorMessage;
import com.appschef.intern.minimarket.mapper.PromoMapper;
import com.appschef.intern.minimarket.repository.ProdukRepository;
import com.appschef.intern.minimarket.repository.PromoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PromoService {
    @Autowired
    private PromoRepository promoRepository;

    @Autowired
    private ProdukRepository produkRepository;

    @Transactional
    public DetailPromoResponse addPromo(AddPromoRequest addPromoRequest) throws ParseException {
        //pengecekan kodePromo duplikat
        if(promoRepository.existsByKodePromo(addPromoRequest.getKodePromo())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, PromoErrorMessage.EXIST.getMessage());
        }

        Produk produk = produkRepository.findByKodeProdukAndIsDeletedFalse(addPromoRequest.getKodeProduk())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ProdukErrorMessage.NOT_FOUND.getMessage()));

        cekJenisDanNilaiPromo(addPromoRequest.getJenis(), addPromoRequest.getNilai());

        Date tanggalMulai = parseDate(addPromoRequest.getTanggalMulai());
        Date tanggalBerakhir = parseDate(addPromoRequest.getTanggalBerakhir());
        tanggalBerakhir = setTimeToEndOfDay(tanggalBerakhir);

        Promo promo = PromoMapper.mapToPromo(addPromoRequest);
        promo.setProduk(produk);
        promo.setTanggalMulai(tanggalMulai);
        promo.setTanggalBerakhir(tanggalBerakhir);

        Promo savedPromo = promoRepository.save(promo);
        return PromoMapper.mapToDetailPromoResponse(savedPromo);
    }

    public DetailPromoResponse getPromoByKodePromo(String kodePromo) {
        Promo promo = promoRepository.findByKodePromo(kodePromo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PromoErrorMessage.NOT_FOUND.getMessage()));

        return PromoMapper.mapToDetailPromoResponse(promo);
    }

    public Page<DetailPromoResponse> getAllPromo(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "namaPromo"));
        Page<Promo> listPromo = promoRepository.findAll(pageable);
        return listPromo.map(PromoMapper::mapToDetailPromoResponse);
    }

//    @Transactional
//    public DetailPromoResponse updatePromo(String kodePromo, UpdatePromoRequest newPromo) throws ParseException {
//        Promo oldPromo = promoRepository.findByKodePromoAndIsDeletedFalse(kodePromo)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PromoErrorMessage.NOT_FOUND.getMessage()));
//
//        Produk produkBaru = produkRepository.findByKodeProdukAndIsDeletedFalse(newPromo.getKodeProduk())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ProdukErrorMessage.NOT_FOUND.getMessage()));
//
//        cekJenisDanNilaiPromo(newPromo.getJenis(), newPromo.getNilai());
//
//        Date tanggalMulaiBaru = parseDate(newPromo.getTanggalMulai());
//        Date tanggalBerakhirBaru = parseDate(newPromo.getTanggalBerakhir());
//        tanggalBerakhirBaru = setTimeToEndOfDay(tanggalBerakhirBaru);
//
//        oldPromo.setNamaPromo(newPromo.getNamaPromo());
//        oldPromo.setJenis(newPromo.getJenis());
//        oldPromo.setNilai(newPromo.getNilai());
//        oldPromo.setTanggalMulai(tanggalMulaiBaru);
//        oldPromo.setTanggalBerakhir(tanggalBerakhirBaru);
//        oldPromo.setProduk(produkBaru);
//
//        Promo updatedPromo = promoRepository.save(oldPromo);
//
//        return PromoMapper.mapToDetailPromoResponse(updatedPromo);
//    }

//    @Transactional
//    public void deletePromo(String kodePromo) {
//        Long idPromo = promoRepository.findIdByKodePromoAndIsDeletedFalse(kodePromo)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PromoErrorMessage.NOT_FOUND.getMessage()));
//
//        int affectedRows = promoRepository.softDeleteById(idPromo);
//        if (affectedRows == 0){
//            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, PromoErrorMessage.GAGAL_HAPUS.getMessage());
//        }
//    }

    private Date parseDate(String tanggal) throws ParseException {
        if (tanggal == null || tanggal.isEmpty()) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        return dateFormat.parse(tanggal);
    }

    private Date setTimeToEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private void cekJenisDanNilaiPromo(String jenis, BigDecimal nilai){
        if(!Objects.equals(jenis, JenisPromo.PERSEN.getJenis()) && !Objects.equals(jenis, JenisPromo.FLAT_NOMINAL.getJenis())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PromoErrorMessage.JENIS_INVALID.getMessage());
        }else if (Objects.equals(jenis, JenisPromo.PERSEN.getJenis()) && nilai.compareTo(new BigDecimal("1.00")) > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PromoErrorMessage.NILAI_INVALID.getMessage());
        }
    }


}
