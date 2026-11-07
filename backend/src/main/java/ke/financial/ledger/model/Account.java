package ke.financial.ledger.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "accounts")
public class Account {
  @Id private UUID id;
  @Column(name="account_number", nullable=false, unique=true) private String accountNumber;
  @Column(name="holder_name", nullable=false) private String holderName;
  @Column(name="balance_cents", nullable=false) private long balanceCents;
  @Version private long version;
  @Column(name="created_at", nullable=false) private Instant createdAt;
  protected Account() {}
  public Account(String number, String holder, long openingCents) {
    if (number == null || number.isBlank() || holder == null || holder.isBlank() || openingCents < 0) throw new IllegalArgumentException("Invalid account");
    id=UUID.randomUUID(); accountNumber=number; holderName=holder; balanceCents=openingCents; createdAt=Instant.now();
  }
  public UUID getId(){return id;} public String getAccountNumber(){return accountNumber;} public String getHolderName(){return holderName;}
  public long getBalanceCents(){return balanceCents;} public void debit(long cents){ if(cents<=0||balanceCents<cents) throw new IllegalArgumentException("Insufficient funds"); balanceCents-=cents; }
  public void credit(long cents){ if(cents<=0) throw new IllegalArgumentException("Amount must be positive"); balanceCents=Math.addExact(balanceCents,cents); }
}
