package ke.financial.ledger.service;
import ke.financial.ledger.model.*; import ke.financial.ledger.repository.LedgerTransactionRepository;
import org.springframework.stereotype.Service; import java.time.Instant;
import org.springframework.cache.annotation.Cacheable;
@Service
public class ComplianceService {
  public static final long HIGH_VALUE_CENTS=50_000_000L;
  private final LedgerTransactionRepository transactions;
  public ComplianceService(LedgerTransactionRepository transactions){this.transactions=transactions;}
  @Cacheable(cacheNames="complianceRules", key="'high-value-cents'")
  public long highValueThreshold(){return HIGH_VALUE_CENTS;}
  public String reason(Account sender,long amount){
    if(amount>=highValueThreshold())return "HIGH_VALUE_STR";
    if(transactions.countBySenderIdAndOccurredAtAfter(sender.getId(), Instant.now().minusSeconds(60))>=3)return "VELOCITY_STRUCTURING";
    return null;
  }
}
