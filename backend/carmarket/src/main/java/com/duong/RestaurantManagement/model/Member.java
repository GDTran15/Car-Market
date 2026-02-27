package com.duong.RestaurantManagement.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "members")
public class Member {
    @Id
    private Long member_id;

    private String firstName;

    private String lastName;

    private int memberPhone;

    private String memberEmail;

    private MembershipRank memberRank;

    private double totalSpent;
}
