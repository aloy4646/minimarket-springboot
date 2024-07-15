package com.appschef.intern.minimarket.controller;

import com.appschef.intern.minimarket.dto.request.AddPromoRequest;
import com.appschef.intern.minimarket.dto.request.UpdatePromoRequest;
import com.appschef.intern.minimarket.dto.response.DetailPromoResponse;
import com.appschef.intern.minimarket.dto.response.DetailPromoResponse;
import com.appschef.intern.minimarket.dto.response.WebResponse;
import com.appschef.intern.minimarket.service.PromoService;
import com.appschef.intern.minimarket.service.ValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/promo")
public class PromoController {
    @Autowired
    private PromoService promoService;

    @Autowired
    private ValidationService validationService;

    @PostMapping("/")
    public ResponseEntity<WebResponse<DetailPromoResponse>> createPromo(@Valid @RequestBody AddPromoRequest addPromoRequest, BindingResult bindingResult){

        //validasi tanggal
        validateTanggal(addPromoRequest.getTanggalMulai(), "tanggalMulai", bindingResult);
        validateTanggal(addPromoRequest.getTanggalBerakhir(), "tanggalBerakhir", bindingResult);

        validationService.validate(bindingResult);

        try{
            DetailPromoResponse savedPromo = promoService.addPromo(addPromoRequest);
            WebResponse<DetailPromoResponse> response = WebResponse.<DetailPromoResponse>builder()
                    .status("success")
                    .data(savedPromo)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<DetailPromoResponse> errorResponse = WebResponse.<DetailPromoResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{kodePromo}")
    public ResponseEntity<WebResponse<DetailPromoResponse>> getPromo(@PathVariable("kodePromo") String kodePromo){
        try{
            DetailPromoResponse promoResponseDTO = promoService.getPromoByKodePromo(kodePromo);
            WebResponse<DetailPromoResponse> response =  WebResponse.<DetailPromoResponse>builder()
                    .status("success")
                    .data(promoResponseDTO)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<DetailPromoResponse> errorResponse = WebResponse.<DetailPromoResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<WebResponse<Page<DetailPromoResponse>>> getAllPromo(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try{
            Page<DetailPromoResponse> listPromo = promoService.getAllPromo(PageRequest.of(page, size));
            WebResponse<Page<DetailPromoResponse>> response =  WebResponse.<Page<DetailPromoResponse>>builder()
                    .status("success")
                    .data(listPromo)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            WebResponse<Page<DetailPromoResponse>> errorResponse = WebResponse.<Page<DetailPromoResponse>>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PutMapping("/{kodePromo}")
//    public ResponseEntity<WebResponse<DetailPromoResponse>> updatePromo(@PathVariable("kodePromo") String kodePromo,
//                                                                          @Valid @RequestBody UpdatePromoRequest updatePromoRequest,
//                                                                          BindingResult bindingResult){
//
//        //validasi tanggal
//        validateTanggal(updatePromoRequest.getTanggalMulai(), "tanggalMulai", bindingResult);
//        validateTanggal(updatePromoRequest.getTanggalBerakhir(), "tanggalBerakhir", bindingResult);
//
//        validationService.validate(bindingResult);
//
//        try{
//            DetailPromoResponse promoResponseDTO = promoService.updatePromo(kodePromo, updatePromoRequest);
//            WebResponse<DetailPromoResponse> response = WebResponse.<DetailPromoResponse>builder()
//                    .status("success")
//                    .data(promoResponseDTO)
//                    .build();
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (ResponseStatusException e) {
//            throw e;
//        } catch (Exception e){
//            WebResponse<DetailPromoResponse> errorResponse = WebResponse.<DetailPromoResponse>builder()
//                    .status("fail")
//                    .error(e.getMessage())
//                    .build();
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping("/{kodePromo}")
//    public ResponseEntity<WebResponse<String>> deleteMember(@PathVariable("kodePromo") String kodePromo){
//        try{
//            promoService.deletePromo(kodePromo);
//            WebResponse<String> response = WebResponse.<String>builder()
//                    .status("success")
//                    .data("Delete berhasil")
//                    .build();
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch (ResponseStatusException e) {
//            throw e;
//        } catch (Exception e){
//            WebResponse<String> errorResponse = WebResponse.<String>builder()
//                    .status("fail")
//                    .error(e.getMessage())
//                    .build();
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

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
}
