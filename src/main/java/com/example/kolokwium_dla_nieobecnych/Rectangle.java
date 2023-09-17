package com.example.kolokwium_dla_nieobecnych;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends FilledShape {
    public Rectangle(Color strokeColor, Point2D topLeft, Point2D rightBottom, Color fillColor) {
        super(strokeColor, topLeft, rightBottom, fillColor);
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
        double width = rightBottom.getX() - topLeft.getX();
        double height = rightBottom.getY() - topLeft.getY();
        gc.fillRect(topLeft.getX(), topLeft.getY(), width, height);
        gc.strokeRect(topLeft.getX(), topLeft.getY(), width, height);
    }
}
