package classes;

import java.util.StringJoiner;

public class Car {
    private String producer;
    private String model;
    private double price;
    private boolean isSale;

    public Car() {
        producer = "Default";
        model = "Default";
        price = 0.0;
        isSale = false;

    }

    public Car(String producer, String model, double price, boolean isSale) {
        this.producer = producer;
        this.model = model;
        this.price = price;
        this.isSale = isSale;
    }

    public double priceAfterInflation(double inflation) {
        price *= 1 + inflation / 100.0;
        return price;
    }

    public void testMethod(int a, double b, String c) {
    }

    public void testMethod(int a, double b, String c, boolean d) {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("producer='" + producer + "'")
                .add("model='" + model + "'")
                .add("price=" + price)
                .add("isSale=" + isSale)
                .toString();
    }
}
