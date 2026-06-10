package com.duong.RestaurantManagement.dto.member.response;

import com.duong.RestaurantManagement.model.MembershipRank;

public record GetMemberInfoDTO(
        String firstName,
        String lastName,
        String memberPhone,
        String memberEmail,
        MembershipRank membershipRank,
        double totalSpent
) {
}
