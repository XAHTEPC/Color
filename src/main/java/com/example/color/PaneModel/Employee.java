package com.example.color.PaneModel;

import com.example.color.DataBase.Postgre;
import com.example.color.Logic.Crypto;
import com.example.color.View.ScrollFront;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class Employee {String id;
    String name;
    String post;
    String exp;
    String sal;
    String inf;
    String age;
    String score;
    String num;
    String login;
    String workID;
    String workName;

    public Employee(String id, String name, String post, String exp,
                    String sal, String inf, String age, String score, String num) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.exp = exp;
        this.sal = sal;
        this.inf = inf;
        this.age = age;
        this.score = score;
        this.num = num;
    }
    public Employee(String id, String name, String workID, String workName) {
        this.id = id;
        this.name = name;
        this.workID = workID;
        this.workName = workName;
    }

    public static Pane getPane(boolean fl, boolean fl2, boolean fl3) throws SQLException, FileNotFoundException, ClassNotFoundException {
        Pane res = new Pane();
        ////fl2 - аналитик
        ////fl - можно ли редачить
        ////fl3 - админ
        if(fl2){
            Button b2 = new Button("Лучшие сотрудники");
            b2.setLayoutX(400);
            b2.setLayoutY(20);
            b2.setOnAction(v ->{
                try {
                    Postgre.task5();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            res.getChildren().addAll(b2);
        }

        Text num = new Text("#");
        num.setLayoutX(20);
        num.setLayoutY(60);
        num.setFont(Font.font("Verdana",13));

        Text em = new Text("ФИО");
        em.setLayoutX(70);
        em.setLayoutY(60);
        em.setFont(Font.font("Verdana",13));

        Text pos = new Text("Должность");
        pos.setLayoutX(230);
        pos.setLayoutY(60);
        pos.setFont(Font.font("Verdana",13));

        Text exp = new Text("Стаж");
        exp.setLayoutX(325);
        exp.setLayoutY(60);
        exp.setFont(Font.font("Verdana",13));

        Text sal = new Text("Зарплата");
        sal.setLayoutX(365);
        sal.setLayoutY(60);
        sal.setFont(Font.font("Verdana",13));

        Text inf = new Text("Информ");
        inf.setLayoutX(440);
        inf.setLayoutY(60);
        inf.setFont(Font.font("Verdana",13));

        Text age = new Text("Возраст");
        age.setLayoutX(530);
        age.setLayoutY(60);
        age.setFont(Font.font("Verdana",13));

        Text sc = new Text("Баллы");
        sc.setLayoutX(590);
        sc.setLayoutY(60);
        sc.setFont(Font.font("Verdana",13));

        Text tel = new Text("Номер");
        tel.setLayoutX(650);
        tel.setLayoutY(60);
        tel.setFont(Font.font("Verdana",13));

        Employee[] mas = Postgre.getAllEmployee();
        int u = 80;
        for(int i=0; i<mas.length;i++, u+=70){
            TextArea num_text = new TextArea();
            num_text.setEditable(false);
            num_text.setText(mas[i].id);
            num_text.setLayoutX(10);
            num_text.setLayoutY(0 + u);
            num_text.setMaxHeight(40);
            num_text.setMaxWidth(30);
            num_text.setMinHeight(40);
            num_text.setMinWidth(30);

            TextArea em_text = new TextArea();
            em_text.setText(mas[i].name);
            em_text.setEditable(false);
            em_text.setLayoutX(50);
            em_text.setLayoutY(0 + u);
            em_text.setMaxHeight(40);
            em_text.setMaxWidth(160);
            em_text.setMinWidth(160);

            TextArea post_text = new TextArea();
            post_text.setText(mas[i].post);
            post_text.setEditable(false);
            post_text.setLayoutX(220);
            post_text.setLayoutY(0 + u);
            post_text.setMaxHeight(40);
            post_text.setMaxWidth(100);
            post_text.setMinHeight(40);
            post_text.setMinWidth(100);

            TextArea exp_text = new TextArea();
            exp_text.setText(mas[i].exp);
            exp_text.setEditable(false);
            exp_text.setLayoutX(330);
            exp_text.setLayoutY(0 + u);
            exp_text.setMaxHeight(40);
            exp_text.setMaxWidth(30);
            exp_text.setMinHeight(40);
            exp_text.setMinWidth(30);

            TextArea sal_text = new TextArea();
            sal_text.setText(mas[i].sal);
            sal_text.setEditable(false);
            sal_text.setLayoutX(370);
            sal_text.setLayoutY(0 + u);
            sal_text.setMaxHeight(40);
            sal_text.setMaxWidth(50);

            TextArea info_text = new TextArea();
            info_text.setText(mas[i].inf);
            info_text.setEditable(false);
            info_text.setLayoutX(430);
            info_text.setLayoutY(0 + u);
            info_text.setMaxHeight(40);
            info_text.setMaxWidth(100);
            info_text.setMinHeight(40);
            info_text.setMinWidth(100);

            TextArea age_text = new TextArea();
            age_text.setText(mas[i].age);
            age_text.setEditable(false);
            age_text.setLayoutX(540);
            age_text.setLayoutY(0 + u);
            age_text.setMaxHeight(40);
            age_text.setMaxWidth(30);
            age_text.setMinHeight(40);
            age_text.setMinWidth(30);

            TextArea score_text = new TextArea();
            score_text.setText(mas[i].score);
            score_text.setEditable(false);
            score_text.setLayoutX(590);
            score_text.setLayoutY(0 + u);
            score_text.setMaxHeight(40);
            score_text.setMaxWidth(40);
            score_text.setMinHeight(40);
            score_text.setMinWidth(40);

            TextArea tel_text = new TextArea();
            tel_text.setText(mas[i].num);
            tel_text.setEditable(false);
            tel_text.setLayoutX(640);
            tel_text.setLayoutY(0 + u);
            tel_text.setMaxHeight(40);
            tel_text.setMaxWidth(90);
            tel_text.setMinHeight(40);
            tel_text.setMinWidth(90);

            final String a = mas[i].id;
            if(fl) {
                FileInputStream Url = new FileInputStream("png/pen.png");
                Image url = new Image(Url);
                ImageView pen = new ImageView(url);

                Button edit = new Button();
                edit.setGraphic(pen);
                edit.setLayoutX(740);
                edit.setLayoutY(0 + u);
                String id = mas[i].id;
                edit.setOnAction(r -> {
                    try {
                        change(id, fl3);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                res.getChildren().add(edit);
            }
            res.getChildren().addAll(num_text,em_text, post_text, exp_text,
                    sal_text, info_text,age_text,score_text,tel_text);
        }
        res.getChildren().addAll(num, em, pos, exp, sal, inf, age, sc, tel);
        return res;
    }
    public static Pane getPane2(boolean fl, boolean fl2, boolean fl3) throws SQLException, FileNotFoundException, ClassNotFoundException {
        Pane res = new Pane();
        ////fl2 - аналитик
        ////fl - можно ли редачить
        ////fl3 - админ

        Text num = new Text("#");
        num.setLayoutX(20);
        num.setLayoutY(60);
        num.setFont(Font.font("Verdana",13));

        Text em = new Text("ФИО");
        em.setLayoutX(70);
        em.setLayoutY(60);
        em.setFont(Font.font("Verdana",13));

        Text pos = new Text("Место работы");
        pos.setLayoutX(230);
        pos.setLayoutY(60);
        pos.setFont(Font.font("Verdana",13));

        Employee[] mas = Postgre.getAllEmployeeWork();
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

            TextArea em_text = new TextArea();
            em_text.setText(mas[i].name);
            em_text.setEditable(false);
            em_text.setLayoutX(50);
            em_text.setLayoutY(0 + u);
            em_text.setMaxHeight(40);
            em_text.setMaxWidth(160);
            em_text.setMinWidth(160);

            TextArea post_text = new TextArea();
            post_text.setText(mas[i].workName);
            post_text.setEditable(false);
            post_text.setLayoutX(220);
            post_text.setLayoutY(0 + u);
            post_text.setMaxHeight(40);
            post_text.setMaxWidth(100);
            post_text.setMinHeight(40);
            post_text.setMinWidth(100);

            FileInputStream Url = new FileInputStream("png/pen.png");
            Image url = new Image(Url);
            ImageView pen = new ImageView(url);

            Button edit = new Button();
            edit.setGraphic(pen);
            edit.setLayoutX(740);
            edit.setLayoutY(0 + u);
            String id = mas[i].id;

            Button del = new Button("Удалить место работы");
            del.setLayoutX(400);
            del.setLayoutY(0 + u);
            String workid = mas[i].workID;
            String empID = mas[i].id;
            edit.setOnAction(r -> {
                try {
                    change(id, fl3);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            del.setOnAction(r -> {
                try {
                    Postgre.delWork(workid,empID);
                    Pane pane = Employee.getPane2(true,true,true);
                    ScrollFront.scrollPane.setContent(pane);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            res.getChildren().addAll(edit,del,post_text,em_text,num_text);
        }
        res.getChildren().addAll(num, em, pos);
        return res;
    }

    public static void change(String id, boolean flagAdmin) throws SQLException, FileNotFoundException, ClassNotFoundException {
        Employee el = Postgre.getEmployee_byID(id);
        String log = Postgre.getEmployeeLogin_byID(el.id);
        Group root_add = new Group();
        Scene scene_add;
        if(flagAdmin)
            scene_add = new Scene(root_add, 450, 659);
        else
            scene_add = new Scene(root_add,450,550);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);

        FileInputStream Url;
        if(flagAdmin) {
            Url = new FileInputStream("png/editStaffAdmin.png");
        }
        else {
            Url = new FileInputStream("png/editStaff.png");
        }
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        root_add.getChildren().add(front);


        TextField name = new TextField();
        name.setText(el.name);
        name.setBackground(null);
        name.setLayoutX(200);
        name.setLayoutY(100);
        name.setMaxHeight(32);
        name.setMinWidth(215);
        name.setStyle("-fx-background-color: transparent;");

        TextField position = new TextField();
        position.setText(el.post);
        position.setLayoutX(200);
        position.setEditable(false);
        if(flagAdmin)
            position.setEditable(true);
        position.setBackground(null);
        position.setLayoutY(144);
        position.setStyle("-fx-background-color: transparent;");
        position.setMaxHeight(32);
        position.setMaxWidth(215);

        TextField exp = new TextField();
        exp.setText(el.exp);
        exp.setBackground(Background.EMPTY);
        exp.setLayoutX(200);
        exp.setLayoutY(190);
        exp.setMaxHeight(32);
        exp.setMaxWidth(215);

        TextField sal = new TextField();
        sal.setText(el.sal);
        sal.setBackground(null);
        sal.setLayoutX(200);
        sal.setLayoutY(234);
        sal.setMaxHeight(32);
        sal.setMaxWidth(215);

        TextField inf = new TextField();
        inf.setBackground(null);
        inf.setLayoutX(200);
        inf.setLayoutY(276);
        inf.setText(el.inf);
        inf.setPrefSize(215,76);

        TextField age = new TextField();
        age.setBackground(null);
        age.setLayoutX(200);
        age.setLayoutY(364);
        age.setMaxHeight(32);
        age.setText(el.age);
        age.setMaxWidth(215);

        TextField score = new TextField();
        score.setBackground(null);
        score.setLayoutX(200);
        score.setLayoutY(408);
        score.setMaxHeight(32);
        score.setText(el.score);
        score.setEditable(false);
        score.setMaxWidth(215);

        TextField tel = new TextField();
        tel.setBackground(null);
        tel.setLayoutX(200);
        tel.setLayoutY(454);
        tel.setText(el.num);
        tel.setMaxHeight(32);
        tel.setMaxWidth(215);

        TextField login = new TextField();
        TextField pass = new TextField();

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(163);
        save.setLayoutY(525);
        save.setPrefSize(113,14);
        Button del = new Button();
        del.setBackground(null);
        del.setLayoutX(268);
        del.setLayoutY(629);
        del.setPrefSize(88,14);
        if(flagAdmin) {
            login.setBackground(null);
            login.setLayoutX(200);
            login.setLayoutY(495);
            login.setMaxHeight(32);
            login.setText(log);
            login.setMaxWidth(215);
            pass.setBackground(null);
            pass.setLayoutX(200);
            pass.setLayoutY(540);
            pass.setMaxHeight(32);
            pass.setMaxWidth(215);
            save.setLayoutY(629);
            save.setLayoutX(95);
            root_add.getChildren().addAll(login,pass,del);
        }

        String finalId = id;
        save.setOnAction(x ->{
            String t1,t2,t3,t4,t5,t6,t7,t8,t9,t10, passSalt;
            t1 = name.getText();
            t2 = position.getText();
            String lvl=el.post;
            if(t2.equals("Администратор"))
                lvl = "Администратор";
            else if(t2.equals("Аналитик"))
                lvl = "Аналитик";
            else  if(t2.equals("Управляющий"))
                lvl = "Управляющий";
            else if(t2.equals("Менеджер"))
                lvl = "Менеджер";
            t3 = exp.getText();
            t4 = sal.getText();
            t5 = inf.getText();
            t6 = age.getText();
            t7 = score.getText();
            t8 = tel.getText();
            t9 = login.getText();
            t10 = pass.getText();
            passSalt = Crypto.hash(t10);
            String oldRole;
            try {
                oldRole = Postgre.getEmployeeRole_byID(id);
                Postgre.UpdateEmployee(finalId,t1,lvl,t3,t4,t5,t6,t7,t8);
                Pane p = Employee.getPane(true, false, flagAdmin);
                ScrollFront.scrollPane.setContent(p);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            boolean flag = t10.isEmpty();
            if(flagAdmin){
                try {
                    Postgre.UpdateUser(finalId,t9,t10,passSalt, t2, flag, oldRole);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            newWindow.close();
        });
        if(flagAdmin) {
            del.setOnAction(t1 -> {
                try {
                    Postgre.delEmployee(finalId);
                    Pane p = Employee.getPane(true, false, flagAdmin);
                    ScrollFront.scrollPane.setContent(p);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                newWindow.close();
            });
        }
        root_add.getChildren().addAll(save,name,position, exp,sal,inf,age,score,tel);
        newWindow.setTitle("Редактирование сотрудника");
        newWindow.setScene(scene_add);
        newWindow.show();
    }
    public static void addEmployee() throws FileNotFoundException {
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 450, 659);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(410,610);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().addAll(pane1);

        FileInputStream Url;
        Url = new FileInputStream("png/addStaffAdmin.png");
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

        TextField position = new TextField();
        position.setLayoutX(200);
        position.setBackground(null);
        position.setLayoutY(144);
        position.setStyle("-fx-background-color: transparent;");
        position.setMaxHeight(32);
        position.setMaxWidth(215);

        TextField exp = new TextField();
        exp.setBackground(Background.EMPTY);
        exp.setLayoutX(200);
        exp.setLayoutY(190);
        exp.setMaxHeight(32);
        exp.setMaxWidth(215);

        TextField sal = new TextField();
        sal.setBackground(null);
        sal.setLayoutX(200);
        sal.setLayoutY(232);
        sal.setMaxHeight(32);
        sal.setMaxWidth(215);

        TextField inf = new TextField();
        inf.setBackground(null);
        inf.setLayoutX(200);
        inf.setLayoutY(276);
        inf.setPrefSize(215,76);

        TextField age = new TextField();
        age.setBackground(null);
        age.setLayoutX(200);
        age.setLayoutY(366);
        age.setMaxHeight(32);
        age.setMaxWidth(215);

        TextField score = new TextField();
        score.setBackground(null);
        score.setLayoutX(200);
        score.setLayoutY(408);
        score.setMaxHeight(32);
        score.setMaxWidth(215);

        TextField tel = new TextField();
        tel.setBackground(null);
        tel.setLayoutX(200);
        tel.setLayoutY(454);
        tel.setMaxHeight(32);
        tel.setMaxWidth(215);

        TextField login = new TextField();
        login.setBackground(null);
        login.setLayoutX(200);
        login.setLayoutY(498);
        login.setMaxHeight(32);
        login.setMaxWidth(215);

        TextField pass = new TextField();
        pass.setBackground(null);
        pass.setLayoutX(200);
        pass.setLayoutY(540);
        pass.setMaxHeight(32);
        pass.setMaxWidth(215);

        Button save = new Button();
        save.setBackground(null);
        save.setLayoutX(163);
        save.setLayoutY(629);
        save.setPrefSize(113,14);
        save.setOnAction(x ->{
            String t1,t2,t3,t4,t5,t6,t7,t8,t9,t10, passSalt;
            t1 = name.getText();
            t2 = position.getText();
            String lvl="Колорист";
            if(t2.equals("Администратор"))
                lvl = "Администратор";
            else if(t2.equals("Аналитик"))
                lvl = "Аналитик";
            else  if(t2.equals("Управляющий"))
                lvl = "Управляющий";
            else if(t2.equals("Менеджер"))
                lvl = "Менеджер";
            t3 = exp.getText();
            t4 = sal.getText();
            t5 = inf.getText();
            t6 = age.getText();
            t7 = score.getText();
            t8 = tel.getText();
            t9 = login.getText();
            t10 = pass.getText();
            passSalt = Crypto.hash(t10);
            if(!t1.isEmpty()&&!t2.isEmpty()&&!t3.isEmpty()&&!t4.isEmpty()&&!t6.isEmpty()&&!t7.isEmpty()&&
                    !t8.isEmpty()&&!t9.isEmpty()&&!t10.isEmpty()) {
                try {
                    Postgre.addEmployee(t1, lvl, t3, t4, t5, t6,t7,t8,t9,passSalt,t10);
                    Pane p = Employee.getPane(true,false, true);
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
        pane1.getChildren().addAll(name,position,exp,sal, save);
        root_add.getChildren().addAll(inf,age,score,tel,login,pass);
        newWindow.setTitle("Добавление сотрудника");
        newWindow.setScene(scene_add);
        newWindow.show();
    }

}
