package com.example.kolokwium_dla_nieobecnych;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Shape {
    protected Color strokeColor;
    protected Point2D topLeft, rightBottom;

    public Shape(Color strokeColor, Point2D topLeft, Point2D rightBottom) {
        this.strokeColor = strokeColor;
        this.topLeft = topLeft;
        this.rightBottom = rightBottom;
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(strokeColor);
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public Point2D getTopLeft() {
        return topLeft;
    }

    public Point2D getRightBottom() {
        return rightBottom;
    }
}
