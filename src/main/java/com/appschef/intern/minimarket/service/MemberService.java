package com.appschef.intern.minimarket.service;

import com.appschef.intern.minimarket.dto.request.CreateMemberRequest;
import com.appschef.intern.minimarket.dto.response.DetailMemberResponse;
import com.appschef.intern.minimarket.entity.Member;
import com.appschef.intern.minimarket.mapper.MemberMapper;
import com.appschef.intern.minimarket.enumMessage.MemberErrorMessage;
import com.appschef.intern.minimarket.repository.MemberRepository;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@AllArgsConstructor
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public DetailMemberResponse createMember(CreateMemberRequest createMemberRequest) {
        Member member = MemberMapper.mapToMember(createMemberRequest);

        // Mendapatkan bulan dan tahun saat ini
        LocalDate currentDate = LocalDate.now();
        String format = currentDate.format(DateTimeFormatter.ofPattern("MMyy"));

        String nomorMemberTerakhir = memberRepository.getNomorMemberTerakhir((format + "%"));
        if(nomorMemberTerakhir == null){
            member.setNomorMember(format + "001");
        }else{
            String tigaAngkaTerakhir = nomorMemberTerakhir.substring(nomorMemberTerakhir.length() - 3);
            Long nomorMemberBaruLong = Long.parseLong(tigaAngkaTerakhir);
            nomorMemberBaruLong++;
            String newMemberNumber = format + String.format("%03d", nomorMemberBaruLong);
            member.setNomorMember(newMemberNumber);
        }

        Member savedMember = memberRepository.save(member);
        return MemberMapper.mapToDetailMemberResponse(savedMember);
    }

    public DetailMemberResponse getMemberByNomorMember(String nomorMember) {
        Member member = memberRepository.findMemberByNomorMemberAndIsDeletedFalse(nomorMember)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MemberErrorMessage.NOT_FOUND.getMessage()));
        return MemberMapper.mapToDetailMemberResponse(member);
    }

    public Page<DetailMemberResponse> getAllMembers(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "namaLengkap"));
        Page<Member> listMember = memberRepository.findByIsDeletedFalse(pageable);
        return listMember.map(MemberMapper::mapToDetailMemberResponse);
    }

    @Transactional
    public DetailMemberResponse updateMember(String nomorMember, CreateMemberRequest newMember) {
        Member oldMember = memberRepository.findMemberByNomorMemberAndIsDeletedFalse(nomorMember)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MemberErrorMessage.NOT_FOUND.getMessage()));

        oldMember.setNamaLengkap(newMember.getNamaLengkap());
        oldMember.setEmail(newMember.getEmail());
        oldMember.setNomorTelepon(newMember.getNomorTelepon());
        oldMember.setAlamat(newMember.getAlamat());
        oldMember.setUpdatedAt(new Date());

        Member updatedMember = memberRepository.save(oldMember);

        return MemberMapper.mapToDetailMemberResponse(updatedMember);
    }

    @Transactional
    public void deleteMember(String nomorMember) {
        Long idMember = memberRepository.findIdByNomorMemberAndIsDeletedFalse(nomorMember)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MemberErrorMessage.NOT_FOUND.getMessage()));

        int affectedRows = memberRepository.softDeleteById(idMember, new Date());
        if (affectedRows == 0){
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, MemberErrorMessage.GAGAL_HAPUS.getMessage());
        }
    }

}
