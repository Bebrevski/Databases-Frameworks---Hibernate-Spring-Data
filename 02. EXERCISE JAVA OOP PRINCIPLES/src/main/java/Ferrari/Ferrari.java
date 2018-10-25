package Ferrari;

public class Ferrari implements Moveable {
    private static final String MODEL = "488-Spider";

    private String driver;

    public Ferrari(String driver) {
        this.driver = driver;
    }


    public String useBrakes() {
        return "Brakes!";
    }

    public String pushTheGasPedal() {
        return "Zadu6avam sA!";
    }

    @Override
    public String toString() {
        return String.format("%s/%s/%s/%s"
                , MODEL
                , useBrakes()
                , pushTheGasPedal()
                , this.driver);
    }
}
