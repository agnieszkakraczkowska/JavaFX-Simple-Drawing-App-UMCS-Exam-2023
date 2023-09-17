package com.example.kolokwium_dla_nieobecnych;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private Canvas canvas;

    @FXML
    private Button newButton;

    @FXML
    private Button openButton;

    @FXML
    private Button saveButton;

    @FXML
    private RadioButton lineButton;

    @FXML
    private RadioButton ellipseButton;

    @FXML
    private RadioButton rectangleButton;

    @FXML
    private ColorPicker fillColorPicker;

    @FXML
    private ColorPicker strokeColorPicker;

    private Color fillColor = Color.TRANSPARENT;
    private Color strokeColor = Color.BLACK;
    private Point2D startPoint, endPoint;
    private GraphicsContext gc;
    private List<Shape> shapes = new ArrayList<>();
    private String currentFilePath = null;

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();

        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseReleased(this::handleMouseReleased);

        fillColorPicker.setOnAction(event -> fillColor = fillColorPicker.getValue());
        strokeColorPicker.setOnAction(event -> strokeColor = strokeColorPicker.getValue());
        newButton.setOnMouseClicked(event -> handleNewButton());
        saveButton.setOnMouseClicked(event -> handleSaveButton());
        openButton.setOnMouseClicked(event -> handleOpenButton());
    }

    private void handleMousePressed(MouseEvent event) {
        startPoint = new Point2D(event.getX(), event.getY());
    }

    private void handleMouseReleased(MouseEvent event) {
        endPoint = new Point2D(event.getX(), event.getY());

        if (lineButton.isSelected()) {
            Line line = new Line(strokeColor,new Point2D(startPoint.getX(),startPoint.getY()),new Point2D(endPoint.getX(),endPoint.getY()));
            line.draw(gc);
            shapes.add(line);
        } else if (ellipseButton.isSelected()) {
            Ellipse ellipse = new Ellipse(strokeColor,new Point2D(startPoint.getX(),startPoint.getY()),new Point2D(endPoint.getX(), endPoint.getY()),fillColor);
            ellipse.draw(gc);
            shapes.add(ellipse);
        } else if (rectangleButton.isSelected()) {
            Rectangle rectangle = new Rectangle(strokeColor,new Point2D(startPoint.getX(),startPoint.getY()),new Point2D(endPoint.getX(),endPoint.getY()),fillColor);
            rectangle.draw(gc);
            shapes.add(rectangle);
        }
    }

    @FXML
    private void handleNewButton() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        shapes.clear();
        currentFilePath = null;
    }

    @FXML
    private void handleSaveButton() {
        if(currentFilePath == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files","*.csv"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if(selectedFile != null) {
                currentFilePath = selectedFile.getAbsolutePath();
            } else {
                return;
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath));
            for(Shape shape : shapes) {
                String type = "";
                Color strokeColor = shape.getStrokeColor();
                String startX = Double.toString(shape.getTopLeft().getX());
                String startY = Double.toString(shape.getTopLeft().getY());
                String endX = Double.toString(shape.getRightBottom().getX());
                String endY = Double.toString(shape.getRightBottom().getY());
                String fillColor = "";

                if(shape instanceof Line) {
                    type = "Line";
                    fillColor = Color.TRANSPARENT.toString();
                } else if(shape instanceof Ellipse) {
                    type = "Ellipse";
                    fillColor = ((FilledShape) shape).getFillColor().toString();
                } else if(shape instanceof Rectangle) {
                    type = "Rectangle";
                    fillColor = ((FilledShape) shape).getFillColor().toString();
                }

                writer.write(type + "," + strokeColor + "," + startX + "," + startY + "," + endX + "," + endY + "," + fillColor + "\n");
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleOpenButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                shapes.clear();
                currentFilePath = selectedFile.getAbsolutePath();

                BufferedReader reader = new BufferedReader(new FileReader(currentFilePath));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 7) {
                        String type = parts[0];
                        Color strokeColor = Color.valueOf(parts[1]);
                        double startX = Double.parseDouble(parts[2]);
                        double startY = Double.parseDouble(parts[3]);
                        double endX = Double.parseDouble(parts[4]);
                        double endY = Double.parseDouble(parts[5]);
                        Color fillColor = Color.valueOf(parts[6]);

                        if ("Line".equals(type)) {
                            Line lineShape = new Line(strokeColor, new Point2D(startX, startY), new Point2D(endX, endY));
                            shapes.add(lineShape);
                        } else if ("Ellipse".equals(type)) {
                            Ellipse ellipseShape = new Ellipse(strokeColor, new Point2D(startX, startY), new Point2D(endX, endY), fillColor);
                            shapes.add(ellipseShape);
                        } else if ("Rectangle".equals(type)) {
                            Rectangle rectangleShape = new Rectangle(strokeColor, new Point2D(startX, startY), new Point2D(endX, endY), fillColor);
                            shapes.add(rectangleShape);
                        }
                    }
                }

                reader.close();

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                for (Shape shape : shapes) {
                    shape.draw(gc);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}