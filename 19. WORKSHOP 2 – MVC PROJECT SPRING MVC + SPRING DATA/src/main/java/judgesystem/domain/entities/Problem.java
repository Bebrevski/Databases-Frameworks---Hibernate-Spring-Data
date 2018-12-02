package judgesystem.entities;

import judgesystem.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "problems")
public class Problem extends BaseEntity {
    private String name;
    private Set<Submission> submissions;
    private Set<User> contestants;
    private Contest contest;
    private Set<MaxResultForProblem> maxResultForProblem;
    private Set<Test> tests;

    public Problem() {
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = Submission.class, mappedBy = "problem")
    public Set<Submission> getSubmissions() {
        return this.submissions;
    }

    public void setSubmissions(Set<Submission> submissions) {
        this.submissions = submissions;
    }

    @ManyToMany(targetEntity = User.class)
    @JoinTable(
            name = "users_problems",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "problem_id", referencedColumnName = "id")
    )
    public Set<User> getContestants() {
        return this.contestants;
    }

    public void setContestants(Set<User> contestants) {
        this.contestants = contestants;
    }

    @ManyToOne(targetEntity = Contest.class)
    public Contest getContest() {
        return this.contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    @OneToMany(targetEntity = MaxResultForProblem.class, mappedBy = "problem")
    public Set<MaxResultForProblem> getMaxResultForProblem() {
        return this.maxResultForProblem;
    }

    public void setMaxResultForProblem(Set<MaxResultForProblem> maxResultForProblem) {
        this.maxResultForProblem = maxResultForProblem;
    }

    @OneToMany(mappedBy = "problem")
    public Set<Test> getTests() {
        return this.tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }
}
