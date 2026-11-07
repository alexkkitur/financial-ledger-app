package ke.financial.ledger.service;
import ke.financial.ledger.model.*; import ke.financial.ledger.repository.*;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.*;
@Service
public class LedgerService {
  private final AccountRepository accounts; private final LedgerTransactionRepository transactions; private final ComplianceService compliance;
  public LedgerService(AccountRepository a, LedgerTransactionRepository t, ComplianceService c){accounts=a;transactions=t;compliance=c;}
  @Transactional(isolation=Isolation.SERIALIZABLE)
  public LedgerTransaction transfer(String from,String to,long cents){
    if(cents<=0||from==null||to==null||from.equals(to))throw new IllegalArgumentException("Transfer must use distinct accounts and a positive cent amount");
    Account sender=accounts.findForUpdate(from).orElseThrow(()->new IllegalArgumentException("Sender not found"));
    Account recipient=accounts.findForUpdate(to).orElseThrow(()->new IllegalArgumentException("Recipient not found"));
    sender.debit(cents); recipient.credit(cents); String reason=compliance.reason(sender,cents);
    return transactions.save(new LedgerTransaction(sender.getId(),recipient.getId(),cents,reason!=null,reason));
  }
}
