package hawk.graphql_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Transaction {
    private BigDecimal settledAmount;
    private String originatorAccountNumber;
    private String beneficiaryAccountNumber;
    private String firstName;
    private String surname;
    private Address originatorAddress;
    private Address beneficiaryAddress;
}
