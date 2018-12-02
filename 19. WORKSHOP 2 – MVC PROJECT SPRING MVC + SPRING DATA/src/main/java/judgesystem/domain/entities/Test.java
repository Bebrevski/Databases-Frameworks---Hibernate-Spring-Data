package judgesystem.domain.entities;

import judgesystem.domain.entities.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "tests")
public class Test extends BaseEntity {
    private String expectedResult;
    private String testContent;
    private Problem problem;

    public Test() {
    }

    @Column(name = "expected_result")
    public String getExpectedResult() {
        return this.expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    @Column(name = "test_content")
    public String getTestContent() {
        return this.testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    @ManyToOne
    public Problem getProblem() {
        return this.problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}
