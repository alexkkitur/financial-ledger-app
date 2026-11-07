package ke.financial.ledger.dto;
import jakarta.validation.constraints.*;
public record TransferRequest(@NotBlank String senderAccount,@NotBlank String recipientAccount,@Positive long amountInCents){}
