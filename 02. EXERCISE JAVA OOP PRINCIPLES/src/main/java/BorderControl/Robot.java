package BorderControl;

public class Robot implements ControlSystem {

    private String model;
    private String id;

    protected Robot(String model, String id) {
        this.model = model;
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean checkId(String lastDigits) {
        return this.id.endsWith(lastDigits);
    }
}
