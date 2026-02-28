package com.duong.RestaurantManagement.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "memberships")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int membershipId;

    @Enumerated(EnumType.STRING)
    private MembershipRank membershipRank;

    private double minSpent;

    private double discountRate;
}
