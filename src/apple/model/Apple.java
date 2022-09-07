package apple.model;

import java.awt.*;

public class Apple {

    private int weight;
    private Color color;
    private String country;

    public Apple() {
    }

    public Apple(int weight) {
        this.weight = weight;
    }

    public Apple(Color color) {
        this.color = color;
    }

    public Apple(int weight, Color color) {
        this.weight = weight;
        this.color = color;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + weight +
                ", color=" + color +
                '}';
    }
}
