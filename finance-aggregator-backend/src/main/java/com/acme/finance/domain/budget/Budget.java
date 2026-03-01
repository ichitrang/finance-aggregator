package com.acme.finance.domain.budget;

import com.acme.finance.domain.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "budgets",
  uniqueConstraints = @UniqueConstraint(name="uk_budget_user_cat_month", columnNames = {"user_id","category","start_month"})
)
public class Budget {

  @Id @GeneratedValue
  private UUID id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name="user_id", nullable=false)
  private User user;

  @Column(nullable=false)
  private String category;

  @Column(name="monthly_limit", nullable=false, precision=14, scale=2)
  private BigDecimal monthlyLimit;

  @Column(name="start_month", nullable=false)
  private LocalDate startMonth;

  @Column(name="created_at", nullable=false)
  private Instant createdAt = Instant.now();

  @Column(name="updated_at", nullable=false)
  private Instant updatedAt = Instant.now();

  @PreUpdate void preUpdate(){ this.updatedAt = Instant.now(); }

}