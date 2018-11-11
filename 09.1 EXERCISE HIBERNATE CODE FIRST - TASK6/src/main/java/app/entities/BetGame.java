package app.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bet_games")
public class BetGame implements Serializable {
    private Game game;
    private Bet bet;
    private ResultPrediction resultPrediction;

    public BetGame(){}

    @Id
    @OneToOne
    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Id
    @OneToOne
    public Bet getBet() {
        return this.bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    @OneToOne
    @JoinColumn(name = "result_prediction", referencedColumnName = "id")
    public ResultPrediction getResultPrediction() {
        return this.resultPrediction;
    }

    public void setResultPrediction(ResultPrediction resultPrediction) {
        this.resultPrediction = resultPrediction;
    }
}
