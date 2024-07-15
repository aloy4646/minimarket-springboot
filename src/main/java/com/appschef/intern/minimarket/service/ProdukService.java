package com.appschef.intern.minimarket.service;

import com.appschef.intern.minimarket.dto.request.AddProdukRequest;
import com.appschef.intern.minimarket.dto.request.UpdateProdukRequest;
import com.appschef.intern.minimarket.dto.response.DetailProdukResponse;
import com.appschef.intern.minimarket.entity.Produk;
import com.appschef.intern.minimarket.mapper.ProdukMapper;
import com.appschef.intern.minimarket.enumMessage.ProdukErrorMessage;
import com.appschef.intern.minimarket.repository.ProdukRepository;
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

import java.util.Date;

@Service
@AllArgsConstructor
public class ProdukService {
    @Autowired
    private ProdukRepository produkRepository;

    @Transactional
    public DetailProdukResponse addProduk(AddProdukRequest addProdukRequest) {
        Produk produk = ProdukMapper.mapToProduk(addProdukRequest);

        //pengecekan kodeProduk duplikat
        if(produkRepository.existsByKodeProduk(produk.getKodeProduk())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, ProdukErrorMessage.EXIST.getMessage());
        }

        Produk savedProduk = produkRepository.save(produk);
        return ProdukMapper.mapToDetailProdukResponse(savedProduk);
    }

    public DetailProdukResponse getProdukByKodeProduk(String kodeProduk) {
        Produk produk = produkRepository.findByKodeProdukAndIsDeletedFalse(kodeProduk)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ProdukErrorMessage.NOT_FOUND.getMessage()));

        return ProdukMapper.mapToDetailProdukResponse(produk);
    }

    public Page<DetailProdukResponse> getAllProduk(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "namaProduk"));
        Page<Produk> listProduk = produkRepository.findByIsDeletedFalse(pageable);
        return listProduk.map(ProdukMapper::mapToDetailProdukResponse);
    }

    @Transactional
    public DetailProdukResponse updateProduk(String kodeProduk, UpdateProdukRequest newProduk) {
        Produk oldProduk = produkRepository.findByKodeProdukAndIsDeletedFalse(kodeProduk)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ProdukErrorMessage.NOT_FOUND.getMessage()));

        oldProduk.setNamaProduk(newProduk.getNamaProduk());
        oldProduk.setHarga(newProduk.getHarga());
        oldProduk.setUpdatedAt(new Date());

        Produk updatedProduk = produkRepository.save(oldProduk);

        return ProdukMapper.mapToDetailProdukResponse(updatedProduk);
    }

    @Transactional
    public void deleteProduk(String kodeProduk) {
        Long idProduk = produkRepository.findIdByKodeProdukAndIsDeletedFalse(kodeProduk)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ProdukErrorMessage.NOT_FOUND.getMessage()));

        int affectedRows = produkRepository.softDeleteById(idProduk);
        if (affectedRows == 0){
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, ProdukErrorMessage.GAGAL_HAPUS.getMessage());
        }
    }


}
