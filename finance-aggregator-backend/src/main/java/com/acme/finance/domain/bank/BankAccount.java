package com.acme.finance.domain.bank;

import com.acme.finance.domain.user.User;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts",
  uniqueConstraints = @UniqueConstraint(name="uk_user_plaid_account", columnNames = {"user_id", "plaid_account_id"})
)
public class BankAccount {

  @Id @GeneratedValue
  private UUID id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String provider = "PLAID";

  @Column(name = "plaid_item_id", nullable = false)
  private String plaidItemId;

  @Column(name = "plaid_access_token_enc", nullable = false, columnDefinition = "text")
  private String plaidAccessTokenEnc;

  @Column(name = "plaid_account_id", nullable = false)
  private String plaidAccountId;

  @Column(name = "institution_name")
  private String institutionName;

  @Column(name = "account_name")
  private String accountName;

  @Column(name = "mask")
  private String mask;

  @Column(name = "account_type")
  private String accountType;

  @Column(name = "account_subtype")
  private String accountSubtype;

  @Column(name = "last_cursor")
  private String lastCursor;

  @Column(name = "last_synced_at")
  private Instant lastSyncedAt;

  @Column(name="created_at", nullable=false)
  private Instant createdAt = Instant.now();

  @Column(name="updated_at", nullable=false)
  private Instant updatedAt = Instant.now();

  @PreUpdate
  void preUpdate(){ this.updatedAt = Instant.now(); }

  // getters/setters
  public UUID getId() { return id; }
  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }
  public String getPlaidItemId() { return plaidItemId; }
  public void setPlaidItemId(String plaidItemId) { this.plaidItemId = plaidItemId; }
  public String getPlaidAccessTokenEnc() { return plaidAccessTokenEnc; }
  public void setPlaidAccessTokenEnc(String plaidAccessTokenEnc) { this.plaidAccessTokenEnc = plaidAccessTokenEnc; }
  public String getPlaidAccountId() { return plaidAccountId; }
  public void setPlaidAccountId(String plaidAccountId) { this.plaidAccountId = plaidAccountId; }
  public Instant getLastSyncedAt() { return lastSyncedAt; }
  public void setLastSyncedAt(Instant lastSyncedAt) { this.lastSyncedAt = lastSyncedAt; }
  public String getLastCursor() { return lastCursor; }
  public void setLastCursor(String lastCursor) { this.lastCursor = lastCursor; }
}