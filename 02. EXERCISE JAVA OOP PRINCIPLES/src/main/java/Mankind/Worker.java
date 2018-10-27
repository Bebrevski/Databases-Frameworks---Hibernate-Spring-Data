package Mankind;

public class Worker extends Human {

    private double weekSalary;
    private double workHoursPerDay;

    public Worker(String firstName, String lastName, double weekSalary, double workHoursPerDay) {
        super(firstName, lastName);
        setWeekSalary(weekSalary);
        setWorkHoursPerDay(workHoursPerDay);
    }

    public void setWeekSalary(double weekSalary) {
        if (weekSalary <= 10) {
            throw new IllegalArgumentException(
                    String.format("Expected value mismatch!Argument: %.2f"
                            , weekSalary));
        }

        this.weekSalary = weekSalary;
    }

    public void setWorkHoursPerDay(double workHoursPerDay) {
        if (workHoursPerDay < 1 || workHoursPerDay > 12) {
            throw new IllegalArgumentException(
                    String.format("Expected value mismatch!Argument: %d"
                            , workHoursPerDay));
        }
        this.workHoursPerDay = workHoursPerDay;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append(super.toString())
                .append(System.lineSeparator())
                .append(String.format("Week salary: %.2f", this.weekSalary))
                .append(System.lineSeparator())
                .append(String.format("Hours per day: %.2f", this.workHoursPerDay))
                .append(System.lineSeparator())
                .append(String.format("Salary per hour: %.2f", this.weekSalary / (this.workHoursPerDay * 7)))
                .append(System.lineSeparator());

        return sb.toString();
    }
}
