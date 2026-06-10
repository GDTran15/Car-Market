package com.duong.RestaurantManagement.mapper;

import com.duong.RestaurantManagement.dto.member.response.GetMemberInfoDTO;
import com.duong.RestaurantManagement.model.Member;

public class MemberMapper {

    public static GetMemberInfoDTO memberToGetMemberInfoDTO(Member member){
        return new GetMemberInfoDTO(
                member.getFirstName(),
                member.getLastName(),
                member.getMemberPhone(),
                member.getMemberEmail(),
                member.getMemberRank(),
                member.getTotalSpent()

        );
    }

}
