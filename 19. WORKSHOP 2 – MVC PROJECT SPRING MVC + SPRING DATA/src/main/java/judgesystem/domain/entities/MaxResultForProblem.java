package judgesystem.domain.entities;

import judgesystem.domain.entities.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "max_results_for_problems")
public class MaxResultForProblem extends BaseEntity {
    private User user;
    private Problem problem;
    private Submission submission;

    public MaxResultForProblem() {
    }

    @ManyToOne
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Problem getProblem() {
        return this.problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    @ManyToOne
    public Submission getSubmission() {
        return this.submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
