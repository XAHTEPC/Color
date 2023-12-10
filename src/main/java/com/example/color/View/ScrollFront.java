package com.example.color.View;

import com.example.color.Front;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ScrollFront {
    public static ScrollPane scrollPane;
    public static Pane getStartFront(Pane paneScroll, int fl) throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/scroll.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);
        scrollPane = new ScrollPane();
        scrollPane.setLayoutX(195);
        scrollPane.setLayoutY(105);
        scrollPane.setMaxHeight(500);
        scrollPane.setMaxWidth(900);
        scrollPane.setMinHeight(500);
        scrollPane.setMinWidth(900);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(paneScroll);
        pane.getChildren().add(scrollPane);

        Button back = new Button();
        back.setLayoutX(546);
        back.setLayoutY(745);
        back.setBackground(null);
        back.setPrefSize(108,22);
        pane.getChildren().add(back);
        back.setOnAction(t1 ->{
            Front.root.getChildren().remove(Front.pane);
            if(fl==1){ //управляющий
                try {
                    Front.pane = SuperVisorFront.getStartFront();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Front.root.getChildren().add(Front.pane);
            }
            if(fl==2){ //аналитик
                try {
                    Front.pane = AnalystFront.getStartFront();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Front.root.getChildren().add(Front.pane);
            }
            if(fl==3){//сотрудник
                try {
                    Front.pane =StaffFront.getStartFront();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Front.root.getChildren().add(Front.pane);
            }
            if(fl==4){//манагер
                try {
                    Front.pane = ManagerFront.getStartFront();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Front.root.getChildren().add(Front.pane);
            }
            if(fl==5){//админ
                try {
                    Front.pane = AdminFront.getStartFront();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Front.root.getChildren().add(Front.pane);
            }
        });

        return pane;
    }
}
