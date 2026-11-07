package ke.financial.ledger.controller;
import ke.financial.ledger.dto.TransferRequest; import ke.financial.ledger.model.LedgerTransaction; import ke.financial.ledger.service.LedgerService;
import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/transfers")
public class TransferController {
  private final LedgerService service; public TransferController(LedgerService s){service=s;}
  @PostMapping @ResponseStatus(HttpStatus.CREATED) public LedgerTransaction transfer(@Valid @RequestBody TransferRequest r){return service.transfer(r.senderAccount(),r.recipientAccount(),r.amountInCents());}
}
