package com.example.color.View;

import com.example.color.DataBase.Postgre;
import com.example.color.Front;
import com.example.color.PaneModel.Employee;
import com.example.color.PaneModel.Establishment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AdminFront {
    static int pushB = 0;
    static Pane pane_scroll;
    public static Pane getStartFront() throws FileNotFoundException {
        Pane pane = new Pane();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        FileInputStream Url;
        Url = new FileInputStream("png/admin.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane.getChildren().add(front);

        Button structure = new Button();
        structure.setBackground(null);
        structure.setLayoutY(343);
        pane.getChildren().add(structure);
        structure.setLayoutX(400);
        structure.setPrefSize(340,63);

        structure.setOnAction(t ->{
            try {
                pane_scroll = Establishment.getPane(true);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = ScrollFront.getStartFront(pane_scroll,5);
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
        employee.setLayoutY(260);
        employee.setPrefSize(360,43);
        employee.setOnAction(t1 ->{
            try {
                pane_scroll = Employee.getPane2(true, false, true);
                Front.root.getChildren().remove(Front.pane);
                Front.pane = ScrollFront.getStartFront(pane_scroll,5);
                Front.root.getChildren().add(Front.pane);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button addStructure = new Button();
        addStructure.setBackground(null);
        addStructure.setLayoutX(750);
        pane.getChildren().add(addStructure);
        addStructure.setLayoutY(343);
        addStructure.setPrefSize(50,63);
        addStructure.setOnAction(t1 -> {
            try {
                Establishment.addStructure();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button addEmployee = new Button();
        addEmployee.setBackground(null);
        addEmployee.setLayoutX(750);
        addEmployee.setLayoutY(260);
        pane.getChildren().add(addEmployee);
        addEmployee.setPrefSize(50,63);
        addEmployee.setOnAction(t1 -> {
            try {
                Employee.addEmployee();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button addWork = new Button();
        addWork.setLayoutY(426);
        addWork.setLayoutX(400);
        addWork.setPrefSize(400,63);
        addWork.setBackground(null);
        pane.getChildren().add(addWork);
        addWork.setOnAction(t1 ->{
            Group root_add = new Group();
            Scene scene_add = new Scene(root_add, 450, 255);
            Stage newWindow = new Stage();
            newWindow.initStyle(StageStyle.DECORATED);
            Pane pane1 = new Pane();
            pane1.setPrefSize(450,255);
            pane1.setLayoutX(0);
            pane1.setLayoutY(0);
            root_add.getChildren().addAll(pane1);

            FileInputStream Url1;

            try {
                Url1 = new FileInputStream("png/addWork.png");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            Image url1 = new Image(Url1);
            ImageView front1 = new ImageView(url1);
            front1.setX(0);
            front1.setY(0);
            pane1.getChildren().add(front1);


            String[] mas = new String[0];
            try {
                mas = Postgre.getEmployee_name();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            ObservableList<String> employee1 = FXCollections.observableArrayList(mas);
            ComboBox<String> comboBox3 = new ComboBox<String>(employee1);
            comboBox3.setMaxWidth(215);
            comboBox3.setMinWidth(215);
            comboBox3.setBackground(null);
            comboBox3.setLayoutX(200);
            comboBox3.setLayoutY(100);

            String[] mas1 = new String[0];
            try {
                mas1 = Postgre.getStructure_name();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            ObservableList<String> work = FXCollections.observableArrayList(mas1);
            ComboBox<String> comboBox2 = new ComboBox<String>(work);
            comboBox2.setMaxWidth(215);
            comboBox2.setMinWidth(215);
            comboBox2.setBackground(null);
            comboBox2.setLayoutX(200);
            comboBox2.setLayoutY(144);

            Button save = new Button();
            save.setLayoutX(169);
            save.setLayoutY(226);
            save.setBackground(null);
            save.setPrefSize(113,14);
            root_add.getChildren().addAll(save,comboBox2,comboBox3);
            save.setOnAction(t2 ->{
                String w1 = comboBox3.getSelectionModel().getSelectedItem();
                String w2 = comboBox2.getSelectionModel().getSelectedItem();
                try {
                    Postgre.addWork(w1,w2);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                newWindow.close();
            });

            newWindow.setTitle("Добавление сотрудника");
            newWindow.setScene(scene_add);
            newWindow.show();
        });
        Button exit = new Button();
        exit.setBackground(null);
        exit.setLayoutX(567);
        exit.setLayoutY(504);
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
