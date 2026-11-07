package ke.financial.ledger.repository;
import ke.financial.ledger.model.LedgerTransaction;
import org.springframework.data.jpa.repository.*;
import java.time.Instant; import java.util.*;
public interface LedgerTransactionRepository extends JpaRepository<LedgerTransaction,UUID> {
  long countBySenderIdAndOccurredAtAfter(UUID senderId, Instant after);
  List<LedgerTransaction> findTop100ByFlaggedTrueOrderByOccurredAtDesc();
}
