package app.ccb.domain.dtos.card;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "card")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CardImportDto {
    @XmlAttribute(name = "status")
    private String cardStatus;
    @XmlAttribute(name = "account-number")
    private String accountNumber;
    @XmlElement(name = "card-number")
    private String cardNumber;

    public CardImportDto() {
    }

    @NotNull
    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @NotNull
    public String getCardStatus() {
        return this.cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
