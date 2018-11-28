package app.ccb.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "bank_account")
public class BankAccount extends BaseEntity{
    private String accountNumber;
    private BigDecimal balance;
    private Client client;
    private List<Card> cards;

    public BankAccount() {
    }

    @Column(name = "account_number", nullable = false)
    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name = "balance")
    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @OneToOne(targetEntity = Client.class)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @OneToMany(targetEntity = Card.class, mappedBy = "bankAccount")
    public List<Card> getCards() {
        return this.cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
