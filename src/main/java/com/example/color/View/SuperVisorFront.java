package com.example.color.View;

import com.example.color.Front;
import com.example.color.PaneModel.Employee;
import com.example.color.PaneModel.Establishment;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class SuperVisorFront {
    static Pane pane_scroll;
    static int pushB = 0;
    public static Pane getStartFront() throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/localmanager.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        Button structure = new Button();
        structure.setBackground(null);
        structure.setLayoutY(385);
        pane.getChildren().add(structure);
        structure.setLayoutX(400);
        structure.setPrefSize(400,63);
        structure.setOnAction(t ->{
            try {

                pane_scroll = Establishment.getPane(true);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = ScrollFront.getStartFront(pane_scroll, 1);
                Front.root.getChildren().add(Front.pane);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button employee = new Button();
        employee.setBackground(null);
        employee.setLayoutX(400);
        pane.getChildren().add(employee);
        employee.setLayoutY(302);
        employee.setPrefSize(400,63);
        employee.setOnAction(t1 ->{
            try {
                pane_scroll = Employee.getPane(true, false, false);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = ScrollFront.getStartFront(pane_scroll,1);
                Front.root.getChildren().add(Front.pane);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        Button exit = new Button();
        exit.setBackground(null);
        exit.setLayoutX(567);
        exit.setLayoutY(463);
        exit.setPrefSize(66,14);
        pane.getChildren().add(exit);
        exit.setOnAction(t ->{
            try {
                Front.exit();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return pane;
    }
}

