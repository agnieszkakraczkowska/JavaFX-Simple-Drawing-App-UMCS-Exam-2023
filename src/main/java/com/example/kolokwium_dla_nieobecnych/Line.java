package com.example.kolokwium_dla_nieobecnych;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Shape {

    public Line(Color strokeColor, Point2D topLeft, Point2D rightBottom) {
        super(strokeColor, topLeft, rightBottom);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(strokeColor);
        gc.strokeLine(topLeft.getX(), topLeft.getY(), rightBottom.getX(), rightBottom.getY());
    }
}
