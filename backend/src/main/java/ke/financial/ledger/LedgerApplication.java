package ke.financial.ledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LedgerApplication {
  public static void main(String[] args) { SpringApplication.run(LedgerApplication.class, args); }
}
