package com.duong.RestaurantManagement.service;

import com.duong.RestaurantManagement.dto.member.request.AddMemberRequest;
import com.duong.RestaurantManagement.dto.member.response.GetMemberInfoDTO;
import com.duong.RestaurantManagement.model.Member;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

public interface MemberService {
    void addMember(@Valid AddMemberRequest addMemberRequest);

    void updateMemberAfterPayment(Member member, double totalPay);

     GetMemberInfoDTO getMemberByPhoneNumber(String phoneNumber);
}
