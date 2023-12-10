package com.example.color.PaneModel;

import com.example.color.DataBase.Postgre;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Service {
    String name;
    String price;
    String id;

    public Service(String id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public static Pane getPane() throws SQLException, FileNotFoundException, ClassNotFoundException {
        Pane res = new Pane();
        Button b1 = new Button("Популярные услуги");
        b1.setLayoutX(400);
        b1.setLayoutY(20);
        b1.setOnAction(v ->{
            try {
                Postgre.task1();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        Button b2 = new Button("Непопулярные услуги");
        b2.setLayoutX(600);
        b2.setLayoutY(20);
        b2.setOnAction(v ->{
            try {
                Postgre.task2();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        res.getChildren().addAll(b1,b2);

        Text num = new Text("#");
        num.setLayoutX(20);
        num.setLayoutY(60);
        num.setFont(Font.font("Verdana",13));

        Text name = new Text("Название услуги");
        name.setLayoutX(70);
        name.setLayoutY(60);
        name.setFont(Font.font("Verdana",13));

        Text price = new Text("Цена");
        price.setLayoutX(220);
        price.setLayoutY(60);
        price.setFont(Font.font("Verdana",13));
        Service[] mas = Postgre.getService();
        int u = 80;
        for(int i=0; i<mas.length;i++, u+=70){
            if(mas[i] == null)
                continue;
            TextArea num_text = new TextArea();
            num_text.setEditable(false);
            num_text.setText(mas[i].id);
            num_text.setLayoutX(10);
            num_text.setLayoutY(0 + u);
            num_text.setMaxHeight(40);
            num_text.setMaxWidth(30);
            num_text.setMinHeight(40);
            num_text.setMinWidth(30);

            TextArea name_text = new TextArea();
            name_text.setText(mas[i].name);
            name_text.setEditable(false);
            name_text.setLayoutX(50);
            name_text.setLayoutY(0 + u);
            name_text.setMaxHeight(40);
            name_text.setMaxWidth(140);
            name_text.setMinWidth(140);

            TextArea price_text = new TextArea();
            price_text.setText(mas[i].price);
            price_text.setEditable(false);
            price_text.setLayoutX(200);
            price_text.setLayoutY(0 + u);
            price_text.setMaxHeight(40);
            price_text.setMaxWidth(140);
            price_text.setMinHeight(40);
            price_text.setMinWidth(140);

            res.getChildren().addAll(num_text,name_text,price_text);
        }
        res.getChildren().addAll(num,name, price);
        return res;
    }
}