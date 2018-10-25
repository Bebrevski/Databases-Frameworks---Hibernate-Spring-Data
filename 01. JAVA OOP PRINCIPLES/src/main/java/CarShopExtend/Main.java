package CarShopExtend;

public class Main {
    public static void main(String[] args) {
        Sellable seat = new Seat("Leon", "Gray", 110, "Spain", 11111.1);
        Rentable audi = new Audi("A5", "grafity", 340, 4, 99.9);

        System.out.println(seat);
        System.out.println(audi);
    }
}
