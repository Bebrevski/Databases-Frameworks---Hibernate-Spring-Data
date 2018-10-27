package Mankind;

public abstract class Human {

    private String firstName;
    private String lastName;

    protected Human(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    private void setFirstName(String firstName) {
        if(!Character.isUpperCase(firstName.charAt(0))) {
        throw new IllegalArgumentException(
                String.format("Expected upper case letter!Argument: %s"
                        , firstName));
        }

        if(firstName.length() < 4) {
        throw new IllegalArgumentException(
                String.format("Expected length at least 4 symbols!Argument: %s"
                        , firstName));
        }

        this.firstName = firstName;
    }

    private void setLastName(String lastName) {
        if(!Character.isUpperCase(lastName.charAt(0))) {
            throw new IllegalArgumentException(
                    String.format("Expected upper case letter!Argument: %s"
                            , lastName));
        }

        if(lastName.length() < 3) {
            throw new IllegalArgumentException(
                    String.format("Expected length at least 4 symbols!Argument: %s"
                            , lastName));
        }

        this.lastName = lastName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb
                .append(String.format("First Name: %s", this.firstName))
                .append(System.lineSeparator())
                .append(String.format("Last Name: %s", this.lastName));

        return sb.toString();
    }
}
