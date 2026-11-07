package ke.financial.ledger.repository;
import ke.financial.ledger.model.Account;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.*;
public interface AccountRepository extends JpaRepository<Account,UUID> {
  @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select a from Account a where a.accountNumber=:number") Optional<Account> findForUpdate(@Param("number") String number);
}
