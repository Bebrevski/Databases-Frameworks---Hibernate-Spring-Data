package Mankind;

public class Student extends Human {

    private String facultyNumber;

    protected Student(String firstName, String lastName, String facultyNumber) {
        super(firstName, lastName);
        setFacultyNumber(facultyNumber);
    }

    private void setFacultyNumber(String facultyNumber) {
        if (facultyNumber.length() < 5 || facultyNumber.length() > 10) {
            throw new IllegalArgumentException("Invalid faculty number!");
        }

        this.facultyNumber = facultyNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append(super.toString())
                .append(System.lineSeparator())
                .append(String.format("Faculty number: %s", this.facultyNumber))
                .append(System.lineSeparator());

        return sb.toString();
    }
}
