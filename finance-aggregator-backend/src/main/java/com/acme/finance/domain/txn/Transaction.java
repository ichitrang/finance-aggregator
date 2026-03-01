package com.acme.finance.domain.txn;

import com.acme.finance.domain.bank.BankAccount;
import com.acme.finance.domain.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transactions",
  uniqueConstraints = @UniqueConstraint(
    name = "uk_txn_provider_id",
    columnNames = {"user_id", "provider", "provider_txn_id"}
  )
)
public class Transaction {

  @Id @GeneratedValue
  private UUID id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "bank_account_id", nullable = false)
  private BankAccount bankAccount;

  @Column(nullable = false)
  private String provider = "PLAID";

  @Column(name = "provider_txn_id", nullable = false)
  private String providerTxnId;

  @Column(name = "posted_date", nullable = false)
  private LocalDate postedDate;

  @Column(nullable = false, precision = 14, scale = 2)
  private BigDecimal amount;

  @Column(name = "currency_code", nullable = false)
  private String currencyCode = "USD";

  @Column(name = "merchant_name")
  private String merchantName;

  @Column(name = "description")
  private String description;

  @Column(name = "category")
  private String category;

  @Column(name = "category_detailed")
  private String categoryDetailed;

  @Column(name = "txn_type", nullable = false)
  private String txnType = "CARD";

  @Column(name = "is_pending", nullable = false)
  private boolean pending = false;

  @Column(name="created_at", nullable=false)
  private Instant createdAt = Instant.now();

  @Column(name="updated_at", nullable=false)
  private Instant updatedAt = Instant.now();

  @PreUpdate
  void preUpdate(){ this.updatedAt = Instant.now(); }

  // getters/setters
  public UUID getId() { return id; }
  public void setUser(User user) { this.user = user; }
  public void setBankAccount(BankAccount bankAccount) { this.bankAccount = bankAccount; }
  public void setProviderTxnId(String providerTxnId) { this.providerTxnId = providerTxnId; }
  public void setPostedDate(LocalDate postedDate) { this.postedDate = postedDate; }
  public void setAmount(BigDecimal amount) { this.amount = amount; }
  public BigDecimal getAmount() { return amount; }
  public LocalDate getPostedDate() { return postedDate; }
  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }
  public void setMerchantName(String merchantName) { this.merchantName = merchantName; }
  public String getMerchantName() { return merchantName; }
}