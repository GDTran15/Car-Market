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
@Table(name = "memberships")
public class Membership {
    @Id
    private int membershipId;

    private MembershipRank membershipRank;

    private double minSpent;

    private double discountRate;
}
