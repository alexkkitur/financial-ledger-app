package ke.financial.ledger.controller;
import ke.financial.ledger.model.LedgerTransaction; import ke.financial.ledger.repository.LedgerTransactionRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping; import org.springframework.stereotype.Controller; import java.util.List;
@Controller
public class ComplianceGraphQlController {
  private final LedgerTransactionRepository repository; public ComplianceGraphQlController(LedgerTransactionRepository r){repository=r;}
  @QueryMapping public List<LedgerTransaction> flaggedTransactions(Integer limit){return repository.findTop100ByFlaggedTrueOrderByOccurredAtDesc().stream().limit(limit==null?100:Math.max(0,Math.min(limit,100))).toList();}
}
