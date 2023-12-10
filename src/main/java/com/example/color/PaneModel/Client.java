package com.example.color.PaneModel;

import com.example.color.DataBase.Postgre;
import com.example.color.View.ScrollFront;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    String id;
    String name;
    String status;
    String bonus;
    static boolean flag;
    static String Name="Чистенько";
    static String[] mas2 = {""};
    static ComboBox<String> comboBox3 = new ComboBox<>();

    public Client(String id, String name, String status, String bonus) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.bonus = bonus;
    }

    public static Pane getPane(boolean fl, boolean fl2, boolean flagStaff) throws SQLException, FileNotFoundException, ClassNotFoundException {
        Pane res = new Pane();
        TextArea name_find = new TextArea();
        name_find.setPromptText("ФИО клиента");
        name_find.setLayoutX(20);
        name_find.setLayoutY(10);
        name_find.setMaxHeight(40);
        name_find.setMaxWidth(310);
        name_find.setMinWidth(310);
        res.getChildren().addAll(name_find);

        Button find = new Button("Поиск клиента");
        find.setLayoutX(350);
        find.setLayoutY(10);
        res.getChildren().add(find);

        Text num = new Text("#");
        num.setLayoutX(30);
        num.setLayoutY(80);
        num.setFont(Font.font("Verdana", 13));

        Text date = new Text("ФИО");
        date.setLayoutX(70);
        date.setLayoutY(80);
        date.setFont(Font.font("Verdana", 13));

        Text description = new Text("Статус");
        description.setLayoutX(400);
        description.setLayoutY(80);
        description.setFont(Font.font("Verdana", 13));

        Text price = new Text("Бонус");
        price.setLayoutX(530);
        price.setLayoutY(80);
        price.setFont(Font.font("Verdana", 13));

        Client[] mas = Postgre.getAllClient();
        int u = 100;
        for (int i = 0; i < mas.length; i++, u += 70) {
            TextArea num_text = new TextArea();
            num_text.setEditable(false);
            num_text.setText(mas[i].id);
            num_text.setLayoutX(20);
            num_text.setLayoutY(0 + u);
            num_text.setMaxHeight(40);
            num_text.setMaxWidth(30);
            num_text.setMinHeight(40);
            num_text.setMinWidth(30);

            TextArea DATA = new TextArea();
            DATA.setText(mas[i].name);
            DATA.setEditable(false);
            DATA.setLayoutX(60);
            DATA.setLayoutY(0 + u);
            DATA.setMaxHeight(40);
            DATA.setMaxWidth(310);
            DATA.setMinWidth(310);

            TextArea description_text = new TextArea();
            description_text.setText(mas[i].status);
            description_text.setEditable(false);
            description_text.setLayoutX(400);
            description_text.setLayoutY(0 + u);
            description_text.setMaxHeight(40);
            description_text.setMaxWidth(100);
            description_text.setMinHeight(40);
            description_text.setMinWidth(80);

            TextArea price_text = new TextArea();
            price_text.setText(mas[i].bonus);
            price_text.setEditable(false);
            price_text.setLayoutX(520);
            price_text.setLayoutY(0 + u);
            price_text.setMaxHeight(40);
            price_text.setMaxWidth(80);

            final String a = mas[i].id;

            if (fl) {
                FileInputStream Url = new FileInputStream("png/pen.png");
                Image url = new Image(Url);
                ImageView pen = new ImageView(url);

                Button edit = new Button();
                edit.setGraphic(pen);
                edit.setLayoutX(610);
                edit.setLayoutY(0 + u);

                edit.setOnAction(t -> {
                    try {
                        change(a,flagStaff);

                    } catch (SQLException | FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                res.getChildren().add(edit);
            }
            if(fl2) {
                Button pos = new Button("Добавить посещение");
                pos.setLayoutX(650);
                pos.setLayoutY(0 + u);

                final String id = mas[i].id;
                pos.setOnAction(val -> {
                    try {
                        add_pos(id,flagStaff);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                res.getChildren().add(pos);
            }
            res.getChildren().addAll(description_text, price_text, DATA, num_text);
        }
        find.setOnAction(value -> {
            String t = name_find.getText();
            System.out.println(t);
            Client[] client = null;
            try {
                client = Postgre.findClient_byNAME(t);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (client == null) {
                name_find.setText("Такого клиента не существует");
                return;
            }
            try {

                print(client,fl, fl2, flagStaff);
            } catch (SQLException | FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        });

        res.getChildren().addAll(price, num, description, date);
        return res;
    }

    public static void change(String id, boolean flag ) throws SQLException, FileNotFoundException {
        Client mas = Postgre.getClient_byID(id);
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 450, 288);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(450,288);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().addAll(pane1);

        FileInputStream Url;
        if(flag) {
            Url = new FileInputStream("png/editClient.png");
        }
        else{
            Url = new FileInputStream("png/editClientStaff.png");
        }
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        pane1.getChildren().add(front);


        TextField name = new TextField();
        name.setBackground(null);
        name.setLayoutX(200);
        name.setLayoutY(100);
        name.setText(mas.name);
        name.setMaxHeight(32);
        name.setMaxWidth(215);
        name.setStyle("-fx-background-color: transparent;");

        TextField status = new TextField();
        status.setLayoutX(200);
        status.setText(mas.status);
        status.setEditable(false);
        status.setBackground(null);
        status.setLayoutY(144);
        status.setStyle("-fx-background-color: transparent;");
        status.setMaxHeight(32);
        status.setMaxWidth(215);

        TextField bonus = new TextField();
        bonus.setBackground(Background.EMPTY);
        bonus.setLayoutX(200);
        bonus.setText(mas.bonus);
        bonus.setEditable(false);
        bonus.setLayoutY(190);
        bonus.setMaxHeight(32);
        bonus.setMaxWidth(215);

        Button save = new Button();
        Button del = new Button();
        if(flag){
            save.setLayoutX(95);
            save.setLayoutY(259);
            save.setBackground(null);
            save.setPrefSize(113,14);
            del.setLayoutX(268);
            del.setLayoutY(259);
            del.setBackground(null);
            del.setPrefSize(88,14);
            root_add.getChildren().addAll(save,del);
        }
        else{
            save.setLayoutX(121);
            save.setLayoutY(245);
            save.setBackground(null);
            save.setPrefSize(150,32);
            root_add.getChildren().add(save);
        }
        save.setOnAction(x -> {
            String t1, t2, t3, t4, t5;
            t1 = name.getText();
            t2 = status.getText();
            t3 = bonus.getText();
            if (t1.isEmpty())
                name.setPromptText("Введите ФИО");
            else {
                try {
                    Postgre.UpdateClient(id, t1, t2, t3);
                    Pane p = Client.getPane( true, true,flag);
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
        });

        del.setOnAction(va -> {
            try {
                Postgre.delClient(id);
                Pane p = Client.getPane(true, true,flag);
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

        root_add.getChildren().addAll(name, status, bonus);
        newWindow.setTitle("Редактирование клиента");
        newWindow.setScene(scene_add);
        newWindow.show();

    }

    public static void addClient(boolean fla, boolean flagStaff) throws SQLException, FileNotFoundException {
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 450, 288);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Pane pane1 = new Pane();
        pane1.setPrefSize(450,288);
        pane1.setLayoutX(0);
        pane1.setLayoutY(0);
        root_add.getChildren().addAll(pane1);

        FileInputStream Url;
        Url = new FileInputStream("png/addClient.png");
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

        TextField status = new TextField();
        status.setLayoutX(200);
        status.setText("Обычный");
        status.setEditable(false);
        status.setBackground(null);
        status.setLayoutY(140);
        status.setStyle("-fx-background-color: transparent;");
        status.setMaxHeight(32);
        status.setMaxWidth(215);

        TextField bonus = new TextField();
        bonus.setBackground(Background.EMPTY);
        bonus.setLayoutX(200);
        bonus.setText("0");
        bonus.setEditable(false);
        bonus.setLayoutY(190);
        bonus.setMaxHeight(32);
        bonus.setMaxWidth(215);

        Button save = new Button();
        save.setLayoutX(169);
        save.setLayoutY(260);
        save.setBackground(null);
        save.setPrefSize(113,14);

        save.setOnAction(x -> {
            String t1;
            t1 = name.getText();
            if (t1.isEmpty())
                name.setPromptText("Введите ФИО");
            else {
                try {
                    Postgre.addClient(t1);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                newWindow.close();
            }
        });

        root_add.getChildren().addAll(save, name, bonus, status);
        newWindow.setTitle("Добавление клиента");
        newWindow.setScene(scene_add);
        newWindow.show();
    }
    static Pane choise;
    static ObservableList<String> service = FXCollections.observableArrayList(mas2);
    public static void add_pos(String id, boolean flagStaff) throws SQLException, FileNotFoundException, ClassNotFoundException {
        Group root_add = new Group();
        Scene scene_add = new Scene(root_add, 450, 372);
        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.DECORATED);
        Client el = Postgre.getClient_byID(id);


        FileInputStream Url;
        Url = new FileInputStream("png/addVisit.png");
        Image url = new Image(Url);
        ImageView front = new ImageView(url);
        front.setX(0);
        front.setY(0);
        root_add.getChildren().add(front);


        TextField name = new TextField();
        name.setBackground(null);
        name.setText(el.name);
        name.setLayoutX(200);
        name.setLayoutY(100);
        name.setMaxHeight(32);
        name.setMaxWidth(215);
        name.setEditable(false);

        String[] mas1 = Postgre.getStructure_name();
        ObservableList<String> work = FXCollections.observableArrayList(mas1);
        ComboBox<String> comboBox2 = new ComboBox<String>(work);
        comboBox2.setMaxWidth(215);
        comboBox2.setMinWidth(215);
        comboBox2.setBackground(null);
        comboBox2.setLayoutX(200);
        comboBox2.setLayoutY(144);

        String[] mas2 = Postgre.getService_name();
        ObservableList<String> serv = FXCollections.observableArrayList(mas2);
        ComboBox<String> comboBox3 = new ComboBox<String>(serv);
        comboBox3.setMaxWidth(215);
        comboBox3.setMinWidth(215);
        comboBox3.setBackground(null);
        comboBox3.setLayoutX(200);
        comboBox3.setLayoutY(190);



        TextField DATA = new TextField();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String string = formatter.format(date);
        DATA.setText(string);
        DATA.setLayoutX(200);
        DATA.setBackground(null);
        DATA.setLayoutY(226);
        DATA.setStyle("-fx-background-color: transparent;");
        DATA.setMaxHeight(32);
        DATA.setMaxWidth(215);

        TextField points = new TextField();
        points.setBackground(null);
        points.setLayoutX(200);
        points.setLayoutY(280);
        points.setMaxHeight(32);
        points.setMaxWidth(215);

        Button save = new Button();
        save.setLayoutX(169);
        save.setLayoutY(343);
        save.setBackground(null);
        save.setPrefSize(113,14);

        String finalId_visit = id;
        save.setOnAction(x ->{
            String t1,t2,t3,t4,t5,t6,t7 = "";
            t1 = name.getText();
            t2 = comboBox2.getSelectionModel().getSelectedItem();
            t3 = comboBox3.getSelectionModel().getSelectedItem();
            t4 = DATA.getText();
            t6 = points.getText();
            String service_id = "1";
            if(chechPos(t4,t6)) {
                String[] arr = {""};
                String lastID="";
                try {
                    arr = Postgre.getEmployee_from(t2);
                    int emp_id = (int) Math.random()*(arr.length-1)+0;
                    String emp = arr[emp_id];
                    Postgre.addReceipt(finalId_visit,t2,t3,t4,t6,emp);
                    lastID = Postgre.getLastReceipt();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                newWindow.close();
            }
            name.setText("Проверьте данные");
        });
        root_add.getChildren().addAll(name,comboBox2,comboBox3);
        root_add.getChildren().addAll(DATA, points, save);
        newWindow.setTitle("Добавить посещение");
        newWindow.setScene(scene_add);
        newWindow.show();
    }

    public static boolean chechPos(String date, String rate) {
        boolean isData = true;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            System.out.println("date error");
            return false;
        }
        boolean isRate = false;
        char[] r = rate.toCharArray();
        if (rate.length() == 1 && r[0] > '0' && r[0] <= '5')
            isRate = true;
        return isData & isRate;
    }


    public static void print(Client[] mas, boolean fla, boolean fla2, boolean flagStaff) throws SQLException, FileNotFoundException {
        Pane res = new Pane();

        TextArea name_find = new TextArea();
        name_find.setPromptText("ФИО клиента");
        name_find.setLayoutX(20);
        name_find.setLayoutY(10);
        name_find.setMaxHeight(40);
        name_find.setMaxWidth(310);
        name_find.setMinWidth(310);
        res.getChildren().addAll(name_find);

        Button find = new Button("Поиск клиента");
        find.setLayoutX(350);
        find.setLayoutY(10);
        res.getChildren().add(find);

        Button back = new Button("Назад");
        back.setLayoutX(600);
        back.setLayoutY(10);
        res.getChildren().add(back);

        Text num = new Text("#");
        num.setLayoutX(30);
        num.setLayoutY(80);
        num.setFont(Font.font("Verdana", 13));

        Text date = new Text("ФИО");
        date.setLayoutX(70);
        date.setLayoutY(80);
        date.setFont(Font.font("Verdana", 13));

        Text description = new Text("Статус");
        description.setLayoutX(400);
        description.setLayoutY(80);
        description.setFont(Font.font("Verdana", 13));

        Text price = new Text("Бонус");
        price.setLayoutX(530);
        price.setLayoutY(80);
        price.setFont(Font.font("Verdana", 13));
        res.getChildren().addAll(num, date, description, price);

        int u = 100;
        for (int i = 0; i < mas.length; i++, u += 70) {
            if (mas[i] == null)
                continue;
            TextArea id_text = new TextArea();
            id_text.setEditable(false);
            id_text.setText(mas[i].id);
            id_text.setLayoutX(20);
            id_text.setLayoutY(0 + u);
            id_text.setMaxHeight(40);
            id_text.setMaxWidth(30);
            id_text.setMinHeight(40);
            id_text.setMinWidth(30);

            TextArea name_text = new TextArea();
            name_text.setText(mas[i].name);
            name_text.setEditable(false);
            name_text.setLayoutX(60);
            name_text.setLayoutY(0 + u);
            name_text.setMaxHeight(40);
            name_text.setMaxWidth(310);
            name_text.setMinWidth(310);

            TextArea status_text = new TextArea();
            status_text.setText(mas[i].status);
            status_text.setEditable(false);
            status_text.setLayoutX(400);
            status_text.setLayoutY(0 + u);
            status_text.setMaxHeight(40);
            status_text.setMaxWidth(100);
            status_text.setMinHeight(40);
            status_text.setMinWidth(80);

            TextArea bonus_text = new TextArea();
            bonus_text.setText(mas[i].bonus);
            bonus_text.setEditable(false);
            bonus_text.setLayoutX(520);
            bonus_text.setLayoutY(0 + u);
            bonus_text.setMaxHeight(40);
            bonus_text.setMaxWidth(80);
            Button edit = new Button();

            if (fla) {
                FileInputStream Url = new FileInputStream("png/pen.png");
                Image url = new Image(Url);
                ImageView pen = new ImageView(url);

                edit.setGraphic(pen);
                edit.setLayoutX(610);
                edit.setLayoutY(0 + u);
                res.getChildren().add(edit);
            }
            final String id = mas[i].id;

            edit.setOnAction(t -> {
                try {
                    change(id,flagStaff);

                } catch (SQLException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            Button pos = new Button("Добавить посещение");
            pos.setLayoutX(650);
            pos.setLayoutY(0 + u);
            if(fla2) {

                pos.setOnAction(val -> {
                    try {
                        add_pos(id, flagStaff);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                res.getChildren().add(pos);
            }
            res.getChildren().addAll(name_text, id_text, status_text, bonus_text);

            ScrollFront.scrollPane.setContent(res);
            back.setOnAction(value -> {
                Pane pane1 = new Pane();
                try {
                    pane1 = Client.getPane(fla, fla2,false);
                    ScrollFront.scrollPane.setContent(pane1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            find.setOnAction(value -> {
                String t = name_find.getText();
                Client[] client = null;
                try {
                    client = Postgre.findClient_byNAME(t);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (client == null) {
                    name_find.setText("Такого клиента не существует");
                    return;
                }
                try {
                    print(client, fla,fla2,flagStaff);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            });
        }
    }
}
