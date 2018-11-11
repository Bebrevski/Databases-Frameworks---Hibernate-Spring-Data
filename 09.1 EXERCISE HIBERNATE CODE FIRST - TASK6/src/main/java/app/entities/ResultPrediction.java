package app.entities;

import javax.persistence.*;

@Entity
@Table(name = "results_predictions")
public class ResultPrediction {
    private int id;
    private PredictionEnum prediction;

    public ResultPrediction(){}

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    public PredictionEnum getPrediction() {
        return this.prediction;
    }

    public void setPrediction(PredictionEnum prediction) {
        this.prediction = prediction;
    }
}
