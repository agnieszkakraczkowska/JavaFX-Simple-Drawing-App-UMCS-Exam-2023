package com.example.kolokwium_dla_nieobecnych;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FilledShape extends Shape {
    protected Color fillColor;

    public FilledShape(Color strokeColor, Point2D topLeft, Point2D rightBottom, Color fillColor) {
        super(strokeColor, topLeft, rightBottom);
        this.fillColor = fillColor;
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        gc.setFill(fillColor);
    }

    public Color getFillColor() {
        return fillColor;
    }
}
