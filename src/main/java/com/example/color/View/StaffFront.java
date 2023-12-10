package com.example.color.View;

import com.example.color.Front;
import com.example.color.PaneModel.Client;
import com.example.color.PaneModel.Visit;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class StaffFront {
    static Pane pane_scroll;
    public static Pane getStartFront() throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/staff.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        Button client = new Button();
        client.setBackground(null);
        client.setLayoutY(302);
        client.setLayoutX(400);
        client.setPrefSize(340,63);
        pane.getChildren().add(client);
        client.setOnAction(t ->{
            try {
                pane_scroll = Client.getPane(false, true,false);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = ScrollFront.getStartFront(pane_scroll,3);
                Front.root.getChildren().add(Front.pane);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        Button add = new Button();
        add.setLayoutX(750);
        add.setLayoutY(302);
        add.setPrefSize(50,63);
        pane.getChildren().add(add);
        add.setBackground(null);
        add.setOnAction(t->{
            try {
                Client.addClient(false, false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button visit = new Button();
        visit.setBackground(null);
        visit.setLayoutX(400);
        visit.setLayoutY(385);
        visit.setPrefSize(400,63);
        pane.getChildren().add(visit);
        visit.setOnAction(t1 ->{
            try {
                pane_scroll = Visit.getPane(false);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = ScrollFront.getStartFront(pane_scroll,3);
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
