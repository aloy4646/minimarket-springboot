package com.appschef.intern.minimarket.mapper;

import com.appschef.intern.minimarket.dto.request.CreateMemberRequest;
import com.appschef.intern.minimarket.dto.response.DetailMemberResponse;
import com.appschef.intern.minimarket.dto.response.TopMemberResponse;
import com.appschef.intern.minimarket.entity.Member;
import com.appschef.intern.minimarket.projection.TopMemberProjection;

import java.util.Date;

public class MemberMapper {
    public static Member mapToMember(CreateMemberRequest createMemberRequest) {

        return new Member(
                null,
                null,
                createMemberRequest.getNamaLengkap(),
                createMemberRequest.getEmail(),
                createMemberRequest.getNomorTelepon(),
                createMemberRequest.getAlamat(),
                0L,
                false,
                new Date(),
                null,
                null
        );
    }

    public static DetailMemberResponse mapToDetailMemberResponse(Member member) {

        return new DetailMemberResponse(
                member.getNamaLengkap(),
                member.getNomorMember(),
                member.getEmail(),
                member.getNomorTelepon(),
                member.getAlamat(),
                member.getPoin()
        );
    }

    public static TopMemberResponse mapToTopMemberResponse(TopMemberProjection topMemberProjection) {
        return new TopMemberResponse(
                topMemberProjection.getNamaLengkap(),
                topMemberProjection.getNomorMember(),
                topMemberProjection.getJumlahPembelian()
        );
    }
}
