package app.ccb.domain.dtos.bankAccount;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bank-accounts")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class BankAccountImportRootDto {

    @XmlElement(name = "bank-account")
    private BankAccountImportDto[] bankAccountImportDtos;

    public BankAccountImportRootDto() {
    }

    public BankAccountImportDto[] getBankAccountImportDtos() {
        return this.bankAccountImportDtos;
    }

    public void setBankAccountImportDtos(BankAccountImportDto[] bankAccountImportDtos) {
        this.bankAccountImportDtos = bankAccountImportDtos;
    }
}
