package CarShopExtend;

public class Seat implements Sellable {

    private String model;
    private String color;
    private int horsePower;
    private String countryProduced;
    private double price;

    protected Seat(String model, String color, int horsePower, String countryProduced, double price) {
        this.model = model;
        this.color = color;
        this.horsePower = horsePower;
        this.countryProduced = countryProduced;
        this.price = price;
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
    public double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return String.format("%s is %s and have %d horse power\nThis is %s produced in %s and have %d tires"
                , this.model
                , this.color
                , this.horsePower
                , this.model
                , this.countryProduced
                , this.TIRES);
    }
}
