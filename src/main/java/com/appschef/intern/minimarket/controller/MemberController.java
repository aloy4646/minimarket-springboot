package com.appschef.intern.minimarket.controller;

import com.appschef.intern.minimarket.dto.request.CreateMemberRequest;
import com.appschef.intern.minimarket.dto.response.DetailMemberResponse;
import com.appschef.intern.minimarket.dto.response.WebResponse;
import com.appschef.intern.minimarket.service.MemberService;
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
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private ValidationService validationService;

    @PostMapping("/")
    public ResponseEntity<WebResponse<DetailMemberResponse>> createMember(@Valid @RequestBody CreateMemberRequest createMemberRequest,
                                                                          BindingResult bindingResult){

        validationService.validate(bindingResult);

        try{
            DetailMemberResponse savedMember = memberService.createMember(createMemberRequest);
            WebResponse<DetailMemberResponse> response = WebResponse.<DetailMemberResponse>builder()
                    .status("success")
                    .data(savedMember)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<DetailMemberResponse> errorResponse = WebResponse.<DetailMemberResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{nomorMember}")
    public ResponseEntity<WebResponse<DetailMemberResponse>> getMember(@PathVariable("nomorMember") String nomorMember){
        try{
            DetailMemberResponse memberResponseDTO = memberService.getMemberByNomorMember(nomorMember);
            WebResponse<DetailMemberResponse> response = WebResponse.<DetailMemberResponse>builder()
                    .status("success")
                    .data(memberResponseDTO)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<DetailMemberResponse> errorResponse = WebResponse.<DetailMemberResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<WebResponse<Page<DetailMemberResponse>>> getAllMember(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try{
            Page<DetailMemberResponse> members = memberService.getAllMembers(PageRequest.of(page, size));
            WebResponse<Page<DetailMemberResponse>> response = WebResponse.<Page<DetailMemberResponse>>builder()
                    .status("success")
                    .data(members)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<Page<DetailMemberResponse>> errorResponse = WebResponse.<Page<DetailMemberResponse>>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{nomorMember}")
    public ResponseEntity<WebResponse<DetailMemberResponse>> updateMember(@PathVariable("nomorMember") String nomorMember,
                                                                       @Valid @RequestBody CreateMemberRequest createMemberRequest,
                                                                       BindingResult bindingResult){

        validationService.validate(bindingResult);

        try{
            DetailMemberResponse memberResponseDTO = memberService.updateMember(nomorMember, createMemberRequest);
            WebResponse<DetailMemberResponse> response = WebResponse.<DetailMemberResponse>builder()
                    .status("success")
                    .data(memberResponseDTO)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e){
            WebResponse<DetailMemberResponse> errorResponse = WebResponse.<DetailMemberResponse>builder()
                    .status("fail")
                    .error(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{nomorMember}")
    public ResponseEntity<WebResponse<String>> deleteMember(@PathVariable("nomorMember") String nomorMember){
        try{
            memberService.deleteMember(nomorMember);
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
