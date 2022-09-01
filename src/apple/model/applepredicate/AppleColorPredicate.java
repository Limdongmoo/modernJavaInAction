package apple.model.applepredicate;

import apple.model.Apple;

import java.awt.*;

public class AppleColorPredicate implements ApplePredicate {

    @Override
    public boolean test(Apple apple) {
        return apple.getColor().equals(Color.GREEN);
    }
}
