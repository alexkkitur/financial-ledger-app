package ke.financial.ledger.model;

import jakarta.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;

@Entity @Table(name="ledger_transactions")
public class LedgerTransaction {
  @Id private final UUID id; @Column(name="transaction_hash", nullable=false, unique=true) private final String transactionHash;
  @Column(name="sender_id", nullable=false) private final UUID senderId; @Column(name="recipient_id", nullable=false) private final UUID recipientId;
  @Column(name="amount_cents", nullable=false) private final long amountInCents; @Column(name="occurred_at", nullable=false) private final Instant occurredAt;
  private final boolean flagged; @Column(name="flag_reason") private final String flagReason;
  protected LedgerTransaction(){id=null;transactionHash=null;senderId=null;recipientId=null;amountInCents=0;occurredAt=null;flagged=false;flagReason=null;}
  public LedgerTransaction(UUID sender, UUID recipient, long cents, boolean suspicious, String reason) {
    if(sender==null||recipient==null||sender.equals(recipient)||cents<=0) throw new IllegalArgumentException("Invalid transaction");
    id=UUID.randomUUID(); senderId=sender; recipientId=recipient; amountInCents=cents; occurredAt=Instant.now(); flagged=suspicious; flagReason=reason;
    transactionHash=hash(id+"|"+sender+"|"+recipient+"|"+cents+"|"+occurredAt);
  }
  private static String hash(String value){try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));}catch(Exception e){throw new IllegalStateException("Hashing unavailable",e);}}
  public UUID getId(){return id;} public String getTransactionHash(){return transactionHash;} public UUID getSenderId(){return senderId;} public UUID getRecipientId(){return recipientId;}
  public long getAmountInCents(){return amountInCents;} public Instant getOccurredAt(){return occurredAt;} public boolean isFlagged(){return flagged;} public String getFlagReason(){return flagReason;}
}
