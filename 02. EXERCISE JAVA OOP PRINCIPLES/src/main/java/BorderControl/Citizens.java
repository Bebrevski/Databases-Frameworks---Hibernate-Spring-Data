package BorderControl;

public class Citizens implements ControlSystem{

    private String name;
    private int age;
    private String id;

    protected Citizens(String name, int age, String id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean checkId(String lastDigits){
        return this.id.endsWith(lastDigits);
    }
}
