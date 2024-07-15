package com.appschef.intern.minimarket.controller;

import com.appschef.intern.minimarket.dto.request.CreateMemberRequest;
import com.appschef.intern.minimarket.dto.request.PembelianRequest;
import com.appschef.intern.minimarket.dto.response.*;
import com.appschef.intern.minimarket.service.PembelianService;
import com.appschef.intern.minimarket.service.ValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/pembelian")
public class PembelianController {
    @Autowired
    PembelianService pembelianService;

    @Autowired
    private ValidationService validationService;

    @PostMapping("/")
    public ResponseEntity<WebResponse<PembelianResponse>> addPembelian(@Valid @RequestBody PembelianRequest pembelianRequest,
                                                                          BindingResult bindingResult){

        validationService.validate(bindingResult);

        try{
            PembelianResponse newPembelian = pembelianService.addPembelian(pembelianRequest);
            WebResponse<PembelianResponse> response = WebResponse.<PembelianResponse>builder()
                    .status("success")
                    .data(newPembelian)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<PembelianResponse> errorResponse = WebResponse.<PembelianResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/laporan-perhari")
    public ResponseEntity<WebResponse<PenjualanPerHariResponse>> laporanPerhari(
            @RequestParam(name = "tanggal", required = false) String tanggal
    ){
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "requestParams");
        if (tanggal == null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tanggal = dateFormat.format(new Date());
        }

        Date tanggalFormated = null;
        if (tanggal != null && !tanggal.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            try {
                tanggalFormated = dateFormat.parse(tanggal);
            } catch (ParseException e) {
                bindingResult.rejectValue("tanggal", "tanggal.invalid", "Format tanggal tidak valid");
            }
        }

        validationService.validate(bindingResult);

        try{
            PenjualanPerHariResponse penjualanPerHariResponse = pembelianService.getLaporanPenjualanPerHari(tanggalFormated);
            WebResponse<PenjualanPerHariResponse> response = WebResponse.<PenjualanPerHariResponse>builder()
                    .status("success")
                    .data(penjualanPerHariResponse)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<PenjualanPerHariResponse> errorResponse = WebResponse.<PenjualanPerHariResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/top-member")
    public ResponseEntity<WebResponse<List<TopMemberResponse>>> topMember () {
        try{
            List<TopMemberResponse> listTopMemberResponse = pembelianService.getTopMemberByTotalNominalPembelian();
            WebResponse<List<TopMemberResponse>> response = WebResponse.<List<TopMemberResponse>>builder()
                    .status("success")
                    .data(listTopMemberResponse)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<List<TopMemberResponse>> errorResponse = WebResponse.<List<TopMemberResponse>>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/top-pembelian/{nomorMember}")
    public ResponseEntity<WebResponse<TopPembelianMemberResponse>> topPembelianMember (@PathVariable("nomorMember") String nomorMember) {
        try{
            TopPembelianMemberResponse topPembelianMemberResponse = pembelianService.getTopPembelianMember(nomorMember);
            WebResponse<TopPembelianMemberResponse> response = WebResponse.<TopPembelianMemberResponse>builder()
                    .status("success")
                    .data(topPembelianMemberResponse)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<TopPembelianMemberResponse> errorResponse = WebResponse.<TopPembelianMemberResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/top-produk")
    public ResponseEntity<WebResponse<List<TopProdukResponse>>> topProduk(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(name = "tanggal_awal", defaultValue = "2024-07-01") String tanggalAwal,
            @RequestParam(name = "tanggal_akhir", required = false) String tanggalAkhir
    ){
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "requestParams");
        if (tanggalAkhir == null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tanggalAkhir = dateFormat.format(new Date());
        }

        //validasi tanggal
        validateTanggal(tanggalAwal, "tanggalAwal", bindingResult);
        validateTanggal(tanggalAkhir, "tanggalAkhir", bindingResult);

        validationService.validate(bindingResult);

        try{
            Date startDate = parseDate(tanggalAwal);
            Date endDate = parseDate(tanggalAkhir);

            List<TopProdukResponse> topProdukResponse = pembelianService.getTopProduk(startDate, endDate);
            WebResponse<List<TopProdukResponse>> response = WebResponse.<List<TopProdukResponse>>builder()
                    .status("success")
                    .data(topProdukResponse)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<List<TopProdukResponse>> errorResponse = WebResponse.<List<TopProdukResponse>>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/laporan-promo/{kodePromo}")
    public ResponseEntity<WebResponse<LaporanPromoResponse>> getLaporanPromo(@PathVariable("kodePromo") String kodePromo){
        try{
            LaporanPromoResponse laporanPromoResponse = pembelianService.getLaporanPromo(kodePromo);
            WebResponse<LaporanPromoResponse> response = WebResponse.<LaporanPromoResponse>builder()
                    .status("success")
                    .data(laporanPromoResponse)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<LaporanPromoResponse> errorResponse = WebResponse.<LaporanPromoResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private void validateTanggal(String tanggal, String fieldName, BindingResult bindingResult) {
        if (tanggal != null && !tanggal.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(tanggal);
            } catch (ParseException e) {
                bindingResult.rejectValue(fieldName, fieldName+".invalid", "Format " + fieldName + " tidak valid");
            }
        }
    }

    private Date parseDate(String tanggal) throws ParseException {
        if (tanggal == null || tanggal.isEmpty()) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        return dateFormat.parse(tanggal);
    }
}
