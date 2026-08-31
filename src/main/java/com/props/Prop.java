package com.props;

import com.GameObject;
import javafx.scene.layout.Pane;

public class Prop extends GameObject {

    public Prop(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public Pane getDisplay() {
        return new Pane();
    }
}
