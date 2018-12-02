package judgesystem.domain.entities;

import judgesystem.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "users")
public class User extends BaseEntity {
    private String username;
    private Set<Submission> submissions;
    private Set<MaxResultForContest> maxResultForContest;
    private Set<MaxResultForProblem> maxResultForProblem;
    private Set<Contest> contests;
    private Set<Problem> problems;

    public User() {
    }

    @Column(name = "username", unique = true)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @OneToMany(targetEntity = Submission.class, mappedBy = "user")
    public Set<Submission> getSubmissions() {
        return this.submissions;
    }

    public void setSubmissions(Set<Submission> submissions) {
        this.submissions = submissions;
    }

    @OneToMany(targetEntity = MaxResultForContest.class, mappedBy = "user")
    public Set<MaxResultForContest> getMaxResultForContest() {
        return this.maxResultForContest;
    }

    public void setMaxResultForContest(Set<MaxResultForContest> maxResultForContest) {
        this.maxResultForContest = maxResultForContest;
    }

    @OneToMany(targetEntity = MaxResultForProblem.class, mappedBy = "user")
    public Set<MaxResultForProblem> getMaxResultForProblem() {
        return this.maxResultForProblem;
    }

    public void setMaxResultForProblem(Set<MaxResultForProblem> maxResultForProblem) {
        this.maxResultForProblem = maxResultForProblem;
    }

    @ManyToMany
    @JoinTable(
            name = "users_contests",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "contest_id", referencedColumnName = "id")
    )
    public Set<Contest> getContests() {
        return this.contests;
    }

    public void setContests(Set<Contest> contests) {
        this.contests = contests;
    }

    @ManyToMany
    @JoinTable(
            name = "users_problems",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "problem_id", referencedColumnName = "id")
    )
    public Set<Problem> getProblems() {
        return this.problems;
    }

    public void setProblems(Set<Problem> problems) {
        this.problems = problems;
    }
}
