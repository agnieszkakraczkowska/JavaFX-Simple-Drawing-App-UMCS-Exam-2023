package com.example.kolokwium_dla_nieobecnych;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ellipse extends FilledShape {
    public Ellipse(Color strokeColor, Point2D topLeft, Point2D rightBottom, Color fillColor) {
        super(strokeColor, topLeft, rightBottom, fillColor);
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        double width = rightBottom.getX() - topLeft.getX();
        double height = rightBottom.getY() - topLeft.getY();
        double centerX = topLeft.getX() + (width / 2);
        double centerY = topLeft.getY() + (height / 2);
        gc.fillOval(centerX - (width / 2), centerY - (height / 2), width, height);
        gc.strokeOval(centerX - (width / 2), centerY - (height / 2), width, height);
    }
}
