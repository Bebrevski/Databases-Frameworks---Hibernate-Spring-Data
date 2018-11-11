package app.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player_statistics")
public class PlayerStatistics implements Serializable {
    private Game game;
    private Player player;
    private int scoredGoals;
    private int playerAssists;
    private int playedMinutesDuringGame;

    public PlayerStatistics(){}

    @Id
    @ManyToOne
    @JoinColumn(name = "game_id")
    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "player_id")
    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Column(name = "goals")
    public int getScoredGoals() {
        return this.scoredGoals;
    }

    public void setScoredGoals(int scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    @Column(name = "assists")
    public int getPlayerAssists() {
        return this.playerAssists;
    }

    public void setPlayerAssists(int playerAssists) {
        this.playerAssists = playerAssists;
    }

    @Column(name = "minutes")
    public int getPlayedMinutesDuringGame() {
        return this.playedMinutesDuringGame;
    }

    public void setPlayedMinutesDuringGame(int playedMinutesDuringGame) {
        this.playedMinutesDuringGame = playedMinutesDuringGame;
    }
}
