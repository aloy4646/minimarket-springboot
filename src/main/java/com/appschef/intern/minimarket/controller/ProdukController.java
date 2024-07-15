package com.appschef.intern.minimarket.controller;

import com.appschef.intern.minimarket.dto.request.AddProdukRequest;
import com.appschef.intern.minimarket.dto.request.UpdateProdukRequest;
import com.appschef.intern.minimarket.dto.response.DetailProdukResponse;
import com.appschef.intern.minimarket.dto.response.WebResponse;
import com.appschef.intern.minimarket.service.ProdukService;
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

@RestController
@RequestMapping("/produk")
public class ProdukController {
    @Autowired
    private ProdukService produkService;

    @Autowired
    private ValidationService validationService;

    @PostMapping("/")
    public ResponseEntity<WebResponse<DetailProdukResponse>> createProduk(@Valid @RequestBody AddProdukRequest addProdukRequest, BindingResult bindingResult){
        validationService.validate(bindingResult);

        try{
            DetailProdukResponse savedProduk = produkService.addProduk(addProdukRequest);
            WebResponse<DetailProdukResponse> response = WebResponse.<DetailProdukResponse>builder()
                    .status("success")
                    .data(savedProduk)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<DetailProdukResponse> errorResponse = WebResponse.<DetailProdukResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{kodeProduk}")
    public ResponseEntity<WebResponse<DetailProdukResponse> >getProduk(@PathVariable("kodeProduk") String kodeProduk){
        try{
            DetailProdukResponse promoResponseDTO = produkService.getProdukByKodeProduk(kodeProduk);
            WebResponse<DetailProdukResponse> response =  WebResponse.<DetailProdukResponse>builder()
                    .status("success")
                    .data(promoResponseDTO)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<DetailProdukResponse> errorResponse = WebResponse.<DetailProdukResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<WebResponse<Page<DetailProdukResponse>>> getAllProduk(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try{
            Page<DetailProdukResponse> listProduk = produkService.getAllProduk(PageRequest.of(page, size));
            WebResponse<Page<DetailProdukResponse>> response =  WebResponse.<Page<DetailProdukResponse>>builder()
                    .status("success")
                    .data(listProduk)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            WebResponse<Page<DetailProdukResponse>> errorResponse = WebResponse.<Page<DetailProdukResponse>>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{kodeProduk}")
    public ResponseEntity<WebResponse<DetailProdukResponse>> updateProduk(@PathVariable("kodeProduk") String kodeProduk,
                                                                   @Valid @RequestBody UpdateProdukRequest updateProdukRequest,
                                                                   BindingResult bindingResult){

        validationService.validate(bindingResult);

        try{
            DetailProdukResponse promoResponseDTO = produkService.updateProduk(kodeProduk, updateProdukRequest);
            WebResponse<DetailProdukResponse> response = WebResponse.<DetailProdukResponse>builder()
                    .status("success")
                    .data(promoResponseDTO)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<DetailProdukResponse> errorResponse = WebResponse.<DetailProdukResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{kodeProduk}")
    public ResponseEntity<WebResponse<String>> deleteMember(@PathVariable("kodeProduk") String kodeProduk){
        try{
            produkService.deleteProduk(kodeProduk);
            WebResponse<String> response = WebResponse.<String>builder()
                    .status("success")
                    .data("Delete berhasil")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<String> errorResponse = WebResponse.<String>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
