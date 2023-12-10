package com.example.color.PaneModel;

import com.example.color.DataBase.Postgre;
import com.example.color.View.ScrollFront;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Establishment {
    String id;
    String name;
    String address;
    String specialty_name;
    String post;
    String tel;
    String num;

    public Establishment(String id, String name, String address, String specialty_name, String post, String tel, String num) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.specialty_name = specialty_name;
        this.post = post;
        this.tel = tel;
        this.num = num;
    }

    public static Pane getPane(boolean fl) throws SQLException, FileNotFoundException, ClassNotFoundException {
        Pane res = new Pane();;

        Text num = new Text("#");
        num.setLayoutX(20);
        num.setLayoutY(60);
        num.setFont(Font.font("Verdana",13));

        Text cl = new Text("Название");
        cl.setLayoutX(70);
        cl.setLayoutY(60);
        cl.setFont(Font.font("Verdana",13));

        Text em = new Text("Адрес");
        em.setLayoutX(220);
        em.setLayoutY(60);
        em.setFont(Font.font("Verdana",13));

        Text ser = new Text("Специализация");
        ser.setLayoutX(430);
        ser.setLayoutY(60);
        ser.setFont(Font.font("Verdana",13));

        Text price = new Text("Индекс");
        price.setLayoutX(550);
        price.setLayoutY(60);
        price.setFont(Font.font("Verdana",13));

        Text str = new Text("Телефон");
        str.setLayoutX(620);
        str.setLayoutY(60);
        str.setFont(Font.font("Verdana",13));

        Text point = new Text("Кол-во \nсотрудников");
        point.setLayoutX(690);
        point.setLayoutY(60);
        point.setFont(Font.font("Verdana",13));

        Establishment[] mas = Postgre.getAllEstablishment();
        int u = 80;
        for(int i=0; i<mas.length;i++, u+=70){
            if(mas[i]==null)
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

            TextArea cl_text = new TextArea();
            cl_text.setText(mas[i].name);
            cl_text.setEditable(false);
            cl_text.setLayoutX(50);
            cl_text.setLayoutY(0 + u);
            cl_text.setMaxHeight(40);
            cl_text.setMaxWidth(140);
            cl_text.setMinWidth(140);

            TextArea emp_text = new TextArea();
            emp_text.setText(mas[i].address);
            emp_text.setEditable(false);
            emp_text.setLayoutX(200);
            emp_text.setLayoutY(0 + u);
            emp_text.setMaxHeight(40);
            emp_text.setMaxWidth(220);
            emp_text.setMinHeight(40);
            emp_text.setMinWidth(220);

            TextArea ser_text = new TextArea();
            ser_text.setText(mas[i].specialty_name);
            ser_text.setEditable(false);
            ser_text.setLayoutX(430);
            ser_text.setLayoutY(0 + u);
            ser_text.setMaxHeight(40);
            ser_text.setMaxWidth(100);
            ser_text.setMinHeight(40);
            ser_text.setMinWidth(100);

            TextArea price_text = new TextArea();
            price_text.setText(mas[i].post);
            price_text.setEditable(false);
            price_text.setLayoutX(540);
            price_text.setLayoutY(0 + u);
            price_text.setMaxHeight(40);
            price_text.setMaxWidth(70);

            TextArea str_text = new TextArea();
            str_text.setText(mas[i].tel);
            str_text.setEditable(false);
            str_text.setLayoutX(620);
            str_text.setLayoutY(0 + u);
            str_text.setMaxHeight(40);
            str_text.setMaxWidth(60);
            str_text.setMinHeight(40);
            str_text.setMinWidth(60);

            TextArea point_text = new TextArea();
            point_text.setText(mas[i].num);
            point_text.setEditable(false);
            point_text.setLayoutX(690);
            point_text.setLayoutY(0 + u);
            point_text.setMaxHeight(40);
            point_text.setMaxWidth(40);
            point_text.setMinHeight(40);
            point_text.setMinWidth(40);
            final String a = mas[i].id;
            if(fl) {

                FileInputStream Url = new FileInputStream("png/pen.png");
                Image url = new Image(Url);
                ImageView pen = new ImageView(url);

                Button edit = new Button();
                edit.setGraphic(pen);
                edit.setLayoutX(740);
                edit.setLayoutY(0 + u);

                Pane finalPane = res;
                String id = mas[i].id;
                edit.setOnAction(r -> {
                    try {
                        change(id);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                res.getChildren().add(edit);
            }
            res.getChildren().addAll(num_text,cl_text,emp_text,ser_text,price_text,str_text,point_text);
        }

        res.getChildren().addAll(num, cl, em, ser, price, str, point);
        return res;
    }

    public static void change(String id) throws SQLException, FileNotFoundException, ClassNotFoundException {
        Establishment el = Postgre.getStructure_byID(id);
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 450, 422);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(450,422);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().addAll(pane1);

        FileInputStream Url;
        Url = new FileInputStream("png/editStructure.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane1.getChildren().add(front);

        TextField name = new TextField();
        name.setText(el.name);
        name.setBackground(null);
        name.setLayoutX(200);
        name.setLayoutY(100);
        name.setMaxHeight(32);
        name.setMaxWidth(215);
        name.setStyle("-fx-background-color: transparent;");

        TextField address = new TextField();
        address.setLayoutX(200);
        address.setText(el.address);
        address.setBackground(null);
        address.setLayoutY(144);
        address.setStyle("-fx-background-color: transparent;");
        address.setMaxHeight(32);
        address.setMaxWidth(215);

        ObservableList<String> type = FXCollections.observableArrayList("Студия окрашивания");
        ComboBox<String> comboBox = new ComboBox<String>(type);
        comboBox.setValue(el.specialty_name);
        comboBox.setLayoutX(200);
        comboBox.setLayoutY(190);
        comboBox.setPrefSize(215,32);
        comboBox.setBackground(null);

        TextField post = new TextField();
        post.setBackground(null);
        post.setText(el.post);
        post.setLayoutX(200);
        post.setLayoutY(234);
        post.setMaxHeight(32);
        post.setMaxWidth(215);

        TextField num = new TextField();
        num.setBackground(null);
        num.setLayoutX(200);
        num.setLayoutY(276);
        num.setText(el.tel);
        num.setMaxHeight(32);
        num.setMaxWidth(215);

        TextField kol_empl = new TextField();
        kol_empl.setBackground(null);
        kol_empl.setLayoutX(200);
        kol_empl.setText(el.num);
        kol_empl.setLayoutY(320);
        kol_empl.setMaxHeight(32);
        kol_empl.setMaxWidth(215);

        Button save = new Button();
        save.setLayoutX(169);
        save.setLayoutY(393);
        save.setPrefSize(113,14);
        save.setBackground(null);

        String finalId = id;
        save.setOnAction(x ->{
            String t1,t2,t3,t4,t5, t6;
            t1 = name.getText();
            t2 = address.getText();
            t3 = comboBox.getSelectionModel().getSelectedItem();
            t4 = post.getText();
            t5 = num.getText();
            t6 = kol_empl.getText();
            if(check(t1,t2,t4,t5,t6)) {
                try {
                    Postgre.UpdateStructure(finalId, t1, t2, t3, t4, t5, t6);
                    Pane p = Establishment.getPane(true);
                    ScrollFront.scrollPane.setContent(p);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                newWindow.close();
            }
            name.setText("Проверьте данные");
        });
        pane1.getChildren().addAll(save,name,address,kol_empl,num,comboBox,post);
        newWindow.setTitle("Редактирование заведения");
        newWindow.setScene(scene_add);
        newWindow.show();
    }

    public static void addStructure() throws FileNotFoundException {
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 450, 422);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(450,422);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().addAll(pane1);

        FileInputStream Url;
        Url = new FileInputStream("png/addStructure.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane1.getChildren().add(front);


        TextField name = new TextField();
        name.setBackground(null);
        name.setLayoutX(200);
        name.setLayoutY(100);
        name.setMaxHeight(32);
        name.setMaxWidth(215);
        name.setStyle("-fx-background-color: transparent;");

        TextField address = new TextField();
        address.setLayoutX(200);
        address.setBackground(null);
        address.setLayoutY(144);
        address.setStyle("-fx-background-color: transparent;");
        address.setMaxHeight(32);
        address.setMaxWidth(215);

        TextField type = new TextField();
        type.setBackground(Background.EMPTY);
        type.setLayoutX(200);
        type.setLayoutY(190);
        type.setMaxHeight(32);
        type.setMaxWidth(215);

        TextField post = new TextField();
        post.setBackground(null);
        post.setLayoutX(200);
        post.setLayoutY(232);
        post.setMaxHeight(32);
        post.setMaxWidth(215);

        TextField num = new TextField();
        num.setBackground(null);
        num.setLayoutX(200);
        num.setLayoutY(276);
        num.setMaxHeight(32);
        num.setMaxWidth(215);

        TextField kol_empl = new TextField();
        kol_empl.setBackground(null);
        kol_empl.setLayoutX(200);
        kol_empl.setLayoutY(320);
        kol_empl.setMaxHeight(32);
        kol_empl.setMaxWidth(215);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(169);
        save.setLayoutY(393);
        save.setPrefSize(113,14);
        save.setOnAction(x ->{
            String t1,t2,t3,t4,t5,t6;
            t1 = name.getText();
            t2 = address.getText();
            t3 = type.getText();
            t4 = post.getText();
            t5 = num.getText();
            t6 = kol_empl.getText();
            if(!t1.isEmpty()&&!t2.isEmpty()&&!t3.isEmpty()&&!t4.isEmpty()&&!t5.isEmpty()) {
                try {
                    Postgre.addStructure(t1, t2, t3, t4, t5, t6);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                newWindow.close();
            }
            name.setText("Проверьте данные");
        });
        pane1.getChildren().addAll(name,address,type,post, save);
        root_add.getChildren().addAll(num,kol_empl);
        newWindow.setTitle("Добавление заведения");
        newWindow.setScene(scene_add);
        newWindow.show();
    }
    public  static boolean check(String t1, String t2, String t3, String t4, String t5){
        boolean bool = t1.isEmpty();
        if(bool)
            return false;
        bool = bool || t2.isEmpty();
        if(bool)
            return false;
        char[] m = t3.toCharArray();
        for(int i=0;i<t3.length();i++){
            if(m[i]>='0'&&m[i]<='9')
                continue;
            return false;
        }
        if(t3.length()!=6)
            return false;
        if(t4.length()!=6)
            return false;
        m = t4.toCharArray();
        for(int i=1;i<t4.length();i++){
            if(m[i]>='0'&&m[i]<='9')
                continue;
            return false;
        }
        m = t5.toCharArray();
        for(int i=0;i<t5.length();i++){
            if(m[i]>='0'&&m[i]<='9')
                continue;
            return false;
        }
        return true;
    }
}

