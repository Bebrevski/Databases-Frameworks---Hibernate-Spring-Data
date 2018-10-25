package CarShopExtend;

import CarShop.Car;

public interface Rentable extends Car {

    int getMinRentDay();

    double getPricePerDay();
}
