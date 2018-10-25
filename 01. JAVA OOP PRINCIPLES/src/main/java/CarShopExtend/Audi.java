package CarShopExtend;

public class Audi implements Rentable {

    private String model;
    private String color;
    private int horsePower;

    private int minRentDays;
    private double pricePerDay;

    protected Audi(String model, String color, int horsePower, int minRentDays, double pricePerDay) {
        this.model = model;
        this.color = color;
        this.horsePower = horsePower;
        this.minRentDays = minRentDays;
        this.pricePerDay = pricePerDay;
    }

    @Override
    public int getMinRentDay() {
        return this.minRentDays;
    }

    @Override
    public double getPricePerDay() {
        return this.pricePerDay;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public int getHorsePower() {
        return this.horsePower;
    }

    @Override
    public String toString() {
        return String.format("%s is %s and have %d horse power"
                , this.model
                , this.color
                , this.horsePower);
    }
}
