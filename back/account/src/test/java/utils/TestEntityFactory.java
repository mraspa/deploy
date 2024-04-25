package utils;

import com.md.account.model.dto.AliasCBUDtoResponse;
import com.md.account.model.dto.AliasOrCBUDtoRequest;
import com.md.account.model.dto.BalanceDtoRequest;
import com.md.account.model.dto.ClientDtoRequest;
import com.md.account.model.dto.ClientDtoResponse;
import com.md.account.model.dto.DocumentNumberDtoRequest;
import com.md.account.model.entity.Account;
import com.md.account.model.entity.Client;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestEntityFactory {
    public static final Long ID = 1L;
    public static final Long ID2 = 2L;
    public static final String ACCOUNT_NUMBER = "123456789112345";
    public static final String ACCOUNT_NUMBER2 = "123456789112346";
    public static final BigDecimal BALANCE = BigDecimal.valueOf(10000);
    public static final String ALIAS = "12345678.mobywallet";
    public static final String ALIAS2 = "12345679.mobywallet";
    public static final String CBU = "1234567891123456789012";
    public static final String CBU2 = "1234567891123456789013";
    public static final LocalDateTime CREATED_AT = LocalDateTime.now();
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(100);
    public static final String NAME = "PEPE";
    public static final String LASTNAME = "ARGENTO";
    public static final String DOCUMENT_NUMBER = "12345678";
    public static final String CUIL = "20123456780";
    public static final String JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";


    public static Account getAccount() {
        return Account.builder()
                .id(ID)
                .accountNumber(ACCOUNT_NUMBER)
                .balance(BALANCE)
                .alias(ALIAS)
                .CBU(CBU)
                .createdAt(CREATED_AT)
                .build();

    }

    public static Account getAnotherAccount() {
        return Account.builder()
                .id(ID2)
                .accountNumber(ACCOUNT_NUMBER2)
                .balance(BALANCE)
                .alias(ALIAS2)
                .CBU(CBU2)
                .createdAt(CREATED_AT)
                .build();

    }

    public static BalanceDtoRequest getBalanceDtoRequest() {
        return BalanceDtoRequest.builder()
                .senderAccountNumber(getAccount().getAccountNumber())
                .receiverAccountNumber(getAnotherAccount().getAccountNumber())
                .balance(AMOUNT)
                .build();
    }

    public static ClientDtoRequest getClientDtoRequest() {
        return ClientDtoRequest.builder()
                .name(NAME)
                .lastName(LASTNAME)
                .documentNumber(DOCUMENT_NUMBER)
                .cuil(CUIL)
                .build();

    }

    public static ClientDtoResponse getClientDtoResponse() {
        return ClientDtoResponse.builder()
                .name(NAME)
                .lastName(LASTNAME)
                .accountNumber(ACCOUNT_NUMBER)
                .cuil(CUIL)
                .build();
    }

    public static Client getClient() {
        return Client.builder()
                .id(ID)
                .account(getAccount())
                .name(NAME)
                .lastName(LASTNAME)
                .documentNumber(DOCUMENT_NUMBER)
                .cuil(CUIL)
                .build();

    }

    public static AliasCBUDtoResponse getAliasCBUDtoResponse() {
        return AliasCBUDtoResponse.builder()
                .alias(ALIAS)
                .CBU(CBU)
                .build();
    }

    public static String getDocumentNumber() {
        return DOCUMENT_NUMBER;
    }

    public static String getJwt() {
        return JWT;
    }

    public static String getName() {
        return NAME;
    }

    public static BigDecimal getBalance() {
        return BALANCE;
    }

    public static AliasOrCBUDtoRequest getAliasOrCBUDtoRequestAlias() {
        return AliasOrCBUDtoRequest.builder()
                .aliasOrCBU(ALIAS).build();
    }

    public static AliasOrCBUDtoRequest getAliasOrCBUDtoRequestCBU() {
        return AliasOrCBUDtoRequest.builder()
                .aliasOrCBU(CBU).build();
    }

    public static DocumentNumberDtoRequest getDocumentNumberDtoRequest() {
        return DocumentNumberDtoRequest.builder()
                .documentNumber(DOCUMENT_NUMBER)
                .build();
    }
}
