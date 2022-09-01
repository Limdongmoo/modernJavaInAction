package apple.model.appleformatter;

import apple.model.Apple;

public class AppleFancyFormatter implements AppleFormatter {

    @Override
    public String accept(Apple apple) {
        String considerWeight = apple.getWeight() > 150 ? "heavy" : "light";
        return considerWeight + "and " + apple.getColor() + "Apple";
    }
}
