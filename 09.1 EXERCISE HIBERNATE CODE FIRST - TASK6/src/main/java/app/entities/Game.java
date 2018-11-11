package app.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "games")
public class Game {
    private int id;
    private Team homeTeam;
    private Team awayTeam;
    private int homeGoals;
    private int awayGoals;
    private Date date;
    private double homeTeamWinBetRate;
    private double awayTeamWinBetRate;
    private double drawGameBetRate;
    private Round round;
    private Competition competition;

    public Game(){}

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "home_team")
    public Team getHomeTeam() {
        return this.homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    @OneToOne
    @JoinColumn(name = "away_team")
    public Team getAwayTeam() {
        return this.awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Column(name = "home_goals")
    public int getHomeGoals() {
        return this.homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    @Column(name = "away_goals")
    public int getAwayGoals() {
        return this.awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    @Column(name = "date")
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "home_team_win_bet_rate")
    public double getHomeTeamWinBetRate() {
        return this.homeTeamWinBetRate;
    }

    public void setHomeTeamWinBetRate(double homeTeamWinBetRate) {
        this.homeTeamWinBetRate = homeTeamWinBetRate;
    }

    @Column(name = "away_team_win_bet_rate")
    public double getAwayTeamWinBetRate() {
        return this.awayTeamWinBetRate;
    }

    public void setAwayTeamWinBetRate(double awayTeamWinBetRate) {
        this.awayTeamWinBetRate = awayTeamWinBetRate;
    }

    @Column(name = "draw_game_bet_rate")
    public double getDrawGameBetRate() {
        return this.drawGameBetRate;
    }

    public void setDrawGameBetRate(double drawHomeBetRate) {
        this.drawGameBetRate = drawHomeBetRate;
    }

    @ManyToOne(targetEntity = Round.class)
    public Round getRound() {
        return this.round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    @ManyToOne(targetEntity = Competition.class)
    public Competition getCompetition() {
        return this.competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
