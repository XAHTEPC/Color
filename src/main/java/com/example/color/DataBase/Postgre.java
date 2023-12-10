package com.example.color.DataBase;

import com.example.color.PaneModel.*;

import java.io.FileNotFoundException;
import java.sql.*;

public class Postgre {
    static Connection data_connection;

    static Connection connection;
    static Statement data_statmt;

    static Statement statmt;
    static ResultSet data_resSet;
    public Postgre(String login, String pass) throws SQLException {
        data_connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Color",login,pass);
        data_statmt = data_connection.createStatement();
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Color","postgres","123");
        statmt = connection.createStatement();
    }

    public static String getRole() throws SQLException {
        data_resSet = data_statmt   .executeQuery("select rolname from pg_user\n" +
                "join pg_auth_members on (pg_user.usesysid=pg_auth_members.member)\n" +
                "join pg_roles on (pg_roles.oid=pg_auth_members.roleid)\n" +
                "where usename = current_user;");
        String role="";
        while (data_resSet.next()){
            role = data_resSet.getString("rolname");
        }
        return role;
    }
    public static Establishment[] getAllEstablishment() throws SQLException {
        String r = getLastStructure();
        int kol = Integer.parseInt(r);
        Establishment[] mas = new Establishment[kol];
        data_resSet = data_statmt.executeQuery("SELECT * \n" + "FROM establishment;");
        int i=0;
        while (data_resSet.next()) {
            String t1 = data_resSet.getString("id");
            String t2 = data_resSet.getString("establishment_name");
            String t3 = data_resSet.getString("address");
            String t4 = data_resSet.getString("specialty_name");
            String t5 = data_resSet.getString("post");
            String t6 = data_resSet.getString("phone_number");
            String t7 = data_resSet.getString("number_of_employees");
            //System.out.println(t1 + " | " + t2 + " | " + t3 + " | " + t4);
            mas[i] = new Establishment(t1,t2,t3,t4,t5,t6,t7);
            i++;
        }
        return mas;
    }
    public static Establishment getStructure_byID(String id) throws SQLException, ClassNotFoundException, FileNotFoundException {
        data_resSet = data_statmt.executeQuery("SELECT * \n" +
                "FROM establishment WHERE id = "+ id + ";");
        Establishment mas = null;
        while (data_resSet.next()) {
            String t1 = data_resSet.getString("id");
            String t2 = data_resSet.getString("establishment_name");
            String t3 = data_resSet.getString("address");
            String t4 = data_resSet.getString("specialty_name");
            String t5 = data_resSet.getString("post");
            String t6 = data_resSet.getString("phone_number");
            String t7 = data_resSet.getString("number_of_employees");
            //System.out.println(t1 + " | " + t2 + " | " + t3 + " | " + t4);
            mas = new Establishment(t1,t2,t3,t4,t5,t6,t7);
        }
        return mas;
        //  System.out.println("-");
    }
    public static void UpdateStructure(String id, String t1, String t2, String t3, String t4, String t5, String t6) throws SQLException {
        //System.out.println(t1+"|"+t2+"|"+ t3+"|" + t4);
        data_statmt.execute("UPDATE establishment " +
                "set establishment_name = '"  + t1 + "'," +
                " address = '"+ t2 + "'," +
                " specialty_name = '" + t3 + "'," +
                " post = '" + t4 + "'," +
                " phone_number = '" + t5 + "'," +
                " number_of_employees = " + t6 +
                " where id = " + id+";");
    }

    public static String getLastStructure() throws SQLException {
        String count;
        data_resSet = statmt.executeQuery("SELECT count(id) as w FROM establishment;");
        data_resSet.next();
        count = data_resSet.getString("w");
        return count;
    }
    public static Employee[] getAllEmployee() throws SQLException, ClassNotFoundException, FileNotFoundException {
        String id = Postgre.getLastEmployee();
        int kol = Integer.parseInt(id);
        Employee[] mas = new Employee[kol];
        data_resSet = data_statmt.executeQuery("SELECT * \n" +
                "FROM employee ORDER BY id asc;");
        int i=0;
        while (data_resSet.next()) {
            String t1 = data_resSet.getString("id");
            String t2 = data_resSet.getString("full_name");
            String t3 = data_resSet.getString("position");
            String t4 = data_resSet.getString("experience");
            String t5 = data_resSet.getString("salary");

            String t6 = data_resSet.getString("brief_information");
            String t7 = data_resSet.getString("age");
            String t8 = data_resSet.getString("rating");
            String t9 = data_resSet.getString("contact_information");
            //System.out.println(t1 + " | " + t2 + " | " + t3 + " | " + t4 + " | " + t5 + " | " + t6 + " | " + t7 + " | " + t8 + " | " + t9);
            mas[i] = new Employee(t1,t2,t3,t4,t5,t6,t7,t8,t9);
            i++;
        }
        System.out.println("-");
        return mas;
    }
    public static String[] getEmployee_name() throws SQLException, ClassNotFoundException, FileNotFoundException {
        String r = getLastEmployee();
        //System.out.println("kol: " + r);
        int kol = Integer.parseInt(r);
        String[] mas = new String[kol];
        data_resSet = statmt.executeQuery("SELECT full_name FROM employee;");
        int i=0;
        while (data_resSet.next()) {
            //System.out.println("i:"+i);
            String t1 = data_resSet.getString("full_name");
            mas[i] = t1;
            i++;
        }
        System.out.println("-");
        return mas;
    }
    public static Employee[] getAllEmployeeWork() throws SQLException, ClassNotFoundException, FileNotFoundException {
        String id = Postgre.getLastEmployee();
        int kol = Integer.parseInt(id);
        Employee[] mas = new Employee[kol];
        data_resSet = data_statmt.executeQuery("SELECT employee.id as w, establishment.id as e, full_name,establishment_name FROM employee left join establishment_employee \n" +
                "                on employee.id = establishment_employee.employee_id join establishment on establishment.id =\n" +
                "                establishment_employee.establishment_id");
        int i=0;
        while (data_resSet.next()) {
            String t1 = data_resSet.getString("w");
            String t2 = data_resSet.getString("full_name");
            String t3 = data_resSet.getString("e");
            String t4 = data_resSet.getString("establishment_name");
            //System.out.println(t1 + " | " + t2 + " | " + t3 + " | " + t4 + " | " + t5 + " | " + t6 + " | " + t7 + " | " + t8 + " | " + t9);
            mas[i] = new Employee(t1,t2,t3,t4);
            i++;
        }
        System.out.println("-");
        return mas;
    }
    public static void delWork(String workID, String empID) throws SQLException {
        statmt.execute("DELETE FROM establishment_employee\n" +
                "\tWHERE establishment_id ='"+workID+"' and employee_id ='"+ empID+"' ;");
    }

    public static String getLastEmployee() throws SQLException {
        String id;
        data_resSet = data_statmt.executeQuery("SELECT count(id) FROM employee;");
        data_resSet.next();
        id = data_resSet.getString("count");
        return id;
    }

    public static Employee getEmployee_byID(String id) throws SQLException, ClassNotFoundException, FileNotFoundException {
        data_resSet = data_statmt.executeQuery("SELECT * \n" +
                "FROM employee WHERE id = "+ id + ";");
        Employee mas = null;
        while (data_resSet.next()) {
            String t1 = data_resSet.getString("id");
            String t2 = data_resSet.getString("full_name");
            String t3 = data_resSet.getString("position");
            String t4 = data_resSet.getString("experience");
            String t5 = data_resSet.getString("salary");
            String t6 = data_resSet.getString("brief_information");
            String t7 = data_resSet.getString("age");
            String t8 = data_resSet.getString("rating");
            String t9 = data_resSet.getString("contact_information");
            mas = new Employee(t1,t2,t3,t4,t5,t6,t7,t8,t9);
        }
        return mas;
    }
    public static void UpdateEmployee(String id, String t1, String t2, String t3, String t4,
                                      String t5, String t6, String t7, String t8) throws SQLException {
        data_statmt.execute("UPDATE employee " +
                "set full_name = '"  + t1 + "'," +
                " position = '"+ t2 + "'," +
                " experience = " + t3 + "," +
                " brief_information = '" + t5 + "'," +
                " age = " + t6 + "," +
                " contact_information = " + t8 +
                " where id = " + id+";");
    }

    public static Client[] getAllClient() throws SQLException, ClassNotFoundException, FileNotFoundException {
        String r = getLastClient();
        int kol = Integer.parseInt(r);
        Client[] mas = new Client[kol];
        data_resSet = data_statmt.executeQuery("SELECT * \n" +
                "FROM client join client_bonus on client.id = client_bonus.client_id;");
        int i=0;
        while (data_resSet.next()) {
            String t1 = data_resSet.getString("id");
            String t2 = data_resSet.getString("full_name");
            String t3 = data_resSet.getString("client_status");
            String t4 = data_resSet.getString("bonus");
            System.out.println(t1 + " | " + t2 + " | " + t3 + " | " + t4);
            mas[i] = new Client(t1,t2,t3,t4);
            i++;
        }
        System.out.println("-");
        return mas;
    }

    public static String getLastClient() throws SQLException {
        String id;
        data_resSet = data_statmt.executeQuery("SELECT count(id) FROM client;");
        data_resSet.next();
        id = data_resSet.getString("count");
        return id;
    }
    public static String getLastClient2() throws SQLException {
        String id;
        data_resSet = data_statmt.executeQuery("SELECT max(id) FROM client;");
        data_resSet.next();
        id = data_resSet.getString("max");
        return id;
    }
    public static Client getClient_byID(String id) throws SQLException {
        data_resSet = data_statmt.executeQuery("SELECT * FROM client join client_bonus on client.id = client_bonus.client_id WHERE client.id = '" + id + "';");
        Client mas = null;
        while(data_resSet.next()) {
            String name = data_resSet.getString("full_name");
            String status = data_resSet.getString("client_status");
            String bonus = data_resSet.getString("bonus");
            mas = new Client(id,name,status,bonus);
        }
        return mas;
    }

    public static void UpdateClient(String t1, String t2, String t3, String t4) throws SQLException {
        System.out.println("------------------------------------------------"+t1+"|"+t2+"|"+ t3+"|" + t4);
        statmt.execute("UPDATE client " +
                "set" +
                " full_name = '"+ t2 + "'" +
                " where id = " + t1+";");
    }

    public static void delClient(String t1) throws SQLException {
        statmt.execute("DELETE FROM receipt"+
                " where client_id = " + t1+";");
        statmt.execute("DELETE FROM client_bonus "+
                " where client_id = " + t1+";");
        statmt.execute("DELETE FROM client "+
                " where id = " + t1+";");
    }

    public  static void addClient(String name) throws SQLException {
        data_statmt.execute("INSERT INTO public.client(\n" +
                "\tfull_name)\n" +
                "\tVALUES ('" + name + "');");
        String id = getLastClient2();
        data_statmt.execute("INSERT INTO public.client_bonus(\n" +
                "\tclient_id)\n" +
                "\tVALUES ('" + id + "');");
    }

    public  static void addStructure(String name, String address, String type, String post, String num, String kol_empl) throws SQLException {
        data_statmt.execute("INSERT INTO public.establishment(\n" +
                "\testablishment_name, address, post, phone_number, number_of_employees, specialty_name)\n" +
                "\tVALUES ('" + name + "', '" + address + "', '" + post + "', '" + num + "', '" + kol_empl + "', '" + type+"' );");
    }
    public  static void addEmployee(String name, String position, String exp, String sal,
                                    String inf,String age,String score, String num, String login, String passSalt, String pass) throws SQLException, FileNotFoundException, ClassNotFoundException {
        statmt.execute("INSERT INTO public.employee(\n" +
                "\tfull_name, experience, salary, brief_information, age, rating, contact_information, \"position\")\n" +
                "\tVALUES ('" + name +"', '" + exp + "', '" + sal + "', '" + inf + "', " + age + ", '" + score + "', " + num + ", '" + position +"');");
        String employee_id = getEmployee_byName(name);
        statmt.execute("INSERT INTO public.users(\n" +
                "\temployee_id, username, password)\n" +
                "\tVALUES ('" + employee_id + "', '" + login + "', '" + passSalt + "');");
        statmt.execute("CREATE ROLE \""+ login  +"\""+
                "\tLOGIN\n" +
                "\tNOSUPERUSER\n" +
                "\tNOCREATEDB\n" +
                "\tNOCREATEROLE\n" +
                "\tINHERIT\n" +
                "\tNOREPLICATION\n" +
                "\tCONNECTION LIMIT -1\n" +
                "\tPASSWORD '" + pass + "';");
        String lvl="Staff";
        if(position.equals("Администратор"))
            lvl = "Administrator";
        else if(position.equals("Аналитик"))
            lvl = "Analyst";
        else  if(position.equals("Управляющий"))
            lvl = "SuperVisor";
        else if(position.equals("Менеджер"))
            lvl = "Manager";
        statmt.execute("GRANT \""+lvl+"\" TO \""+ login+"\" WITH ADMIN OPTION;");
    }

    public static void UpdateUser(String id, String login, String pass, String passSalt, String position, boolean fl, String oldRole) throws SQLException {
        String lvl="Staff";
        if(position.equals("Администратор"))
            lvl = "Administrator";
        else if(position.equals("Аналитик"))
            lvl = "Analyst";
        else  if(position.equals("Управляющий"))
            lvl = "SuperVisor";
        else if(position.equals("Менеджер"))
            lvl = "Manager";
        String oldLogin = getEmployeeLogin_byID(id);
        if(!oldLogin.equals(login))
            statmt.execute("ALTER ROLE \""+ oldLogin +"\" RENAME TO \""+login+"\";");
        if(!oldRole.equals(lvl)) {
            statmt.execute("REVOKE \"" + oldRole + "\" FROM \"" + login + "\";");
            statmt.execute("GRANT \"" + lvl + "\" TO \"" + login + "\" WITH ADMIN OPTION;");
        }
        if(fl){
            statmt.execute("UPDATE users " +
                    "set username = '"  + login + "'" +
                    " where employee_id = " + id+";");
        }
        else {
            statmt.execute("UPDATE users " +
                    "set username = '" + login + "'," +
                    " password = '" + passSalt + "'" +
                    " where employee_id = " + id + ";");
            statmt.execute("ALTER ROLE \"" + login + "\"\n" +
                    "\tPASSWORD '" + pass + "';");
        }
    }

    public static String getEmployeeLogin_byID(String id) throws SQLException {
        String login ="";
        data_resSet = statmt.executeQuery("Select username from public.users where employee_id ='"+ id+"';");
        while (data_resSet.next()){
            login = data_resSet.getString("username");
        }
        return login;
    }
    public static String getEmployeeRole_byID(String id) throws SQLException {
        String role ="";
        data_resSet = statmt.executeQuery("Select \"position\" from public.employee where id ='"+ id+"';");
        while (data_resSet.next()){
            role = data_resSet.getString("position");
        }
        System.out.println("ollllld"+ role);
        if(role.equals("Аналитик"))
            role = "Analyst";
        else if(role.equals("Администратор"))
            role = "Administrator";
        else if(role.equals("Менеджер"))
            role = "Manager";
        else if(role.equals("Управляющий"))
            role = "SuperVisor";
        else
            role = "Staff";
        return role;
    }

    public static void delEmployee(String t1) throws SQLException, FileNotFoundException, ClassNotFoundException {
        statmt.execute("DELETE FROM booking "+
                " where master_id = " + t1+";");
        String t2 = getEmployeeLogin_byID(t1);
        statmt.execute("DELETE FROM users "+
                " where employee_id = " + t1+";");
        statmt.execute("DELETE FROM establishment_employee "+
                " where employee_id = " + t1+";");

        statmt.execute("DELETE FROM employee "+
                " where id = " + t1+";");
        statmt.execute("DROP ROLE \"" + t2 + "\";");
    }


    public static String[] getStructure_name() throws SQLException, ClassNotFoundException, FileNotFoundException {
        String r = getLastStructure();
        int kol = Integer.parseInt(r);
        String[] mas = new String[kol];
        data_resSet = statmt.executeQuery("SELECT establishment_name FROM establishment;");
        int i=0;
        while (data_resSet.next()) {
            //System.out.println("i:"+i);
            String t1 = data_resSet.getString("establishment_name");
            mas[i] = t1;
            i++;
        }
        System.out.println("-");
        return mas;
    }

    public static String getLastService() throws SQLException {
        String id ="";
        data_resSet = statmt.executeQuery("SELECT count(id) FROM service;");
        while(data_resSet.next())
            id = data_resSet.getString("count");
        return id;
    }

    public static String getPrice(String name) throws SQLException, ClassNotFoundException, FileNotFoundException {
        statmt = connection.createStatement();
        data_resSet = statmt.executeQuery("SELECT price FROM service WHERE name = '"+ name + "';");
        String t ="";
        while (data_resSet.next()) {
            t = data_resSet.getString("price");
        }
        return t;
        //  System.out.println("-");
    }
    public static String countEmployee(String t) throws SQLException {
        String a="";
        data_resSet = statmt.executeQuery("SELECT count(employee_id) from establishment_employee\n" +
                "join employee on employee.id = establishment_employee.employee_id\n" +
                "where \"position\"<>'управляющий' and establishment_employee.establishment_id = '"+t+"';");
        while (data_resSet.next()){
            a = data_resSet.getString("count");
        }
        return a;
    }
    public static void addReceipt(String client_id, String establishment_id, String price,
                                  String date, String score, String master_id) throws SQLException, FileNotFoundException, ClassNotFoundException {
        establishment_id = getStructure_byNAME(establishment_id);
        String price1 = getPricebyName(price);
        String priceId = getPriceID(price);
        statmt.execute("INSERT INTO public.receipt(\n" +
                "\tclient_id, establishment_id, price, date, score, master_id, service_id)\n" +
                "\tVALUES ('"+client_id+"', '"+establishment_id +"', '"+ price1+"', " +
                "'"+date+"', '"+score+"', '"+ master_id+"', '"+ priceId+"')");
    }
    public static String getPriceID(String id) throws SQLException {
        String pr = "";
        data_resSet = statmt.executeQuery("SELECT id, name, price\n" +
                "\tFROM public.service WHERE name = '"+ id+"' ;");
        while (data_resSet.next()){
            pr = data_resSet.getString("id");
        }
        return pr;
    }
    public static String getPricebyName(String price) throws SQLException {
        String pr = "";
        data_resSet = statmt.executeQuery("SELECT id, name, price\n" +
                "\tFROM public.service WHERE name = '"+ price+"' ;");
        while (data_resSet.next()){
            pr = data_resSet.getString("price");
        }
        return pr;
    }
    public static String getLastReceipt() throws SQLException {
        String a="";
        data_resSet = statmt.executeQuery("SELECT max(id_receipt) FROM receipt");
        while (data_resSet.next()){
            a = data_resSet.getString("max");
        }
        return a;
    }
    public static String[] getEmployee_from(String t) throws SQLException, FileNotFoundException, ClassNotFoundException {
        t = getStructure_byNAME(t);
        String k = countEmployee(t);
        int kol = Integer.parseInt(k);
        String[] mas = new String[kol];
        int i=0;
        data_resSet = statmt.executeQuery("SELECT employee.id as w from establishment_employee\n" +
                "join employee on employee.id = establishment_employee.employee_id\n" +
                "where \"position\"<>'управляющий' and establishment_employee.establishment_id = '" + t+"';");
        while (data_resSet.next()){
            String id = data_resSet.getString("w");
            mas[i] = id;
        }
        return mas;

    }
    public static String getLastBooking() throws SQLException {
        String a="";
        data_resSet = statmt.executeQuery("SELECT max(id_booking) FROM booking");
        while (data_resSet.next()){
            a = data_resSet.getString("max");
        }
        return a;
    }

    public  static void addBooking(String client_id, String service_id, String date, String score, String emp_id, String lastID) throws SQLException, FileNotFoundException, ClassNotFoundException {
        statmt.execute("INSERT INTO public.booking(\n" +
                "\tclient_id, service_id, booking_date, master_rating, master_id)\n" +
                "\tVALUES ('" +  client_id + "', '" + service_id + "', '" + date + "', '" + score + "', '" + emp_id + "');");
        String t = getLastBooking();
        statmt.execute("INSERT INTO public.receipt_booking(\n" +
                "\tid_booking, id_receipt)\n" +
                "\tVALUES ('"+ t+"', '"+ lastID+"');");
    }
    public static void UpdateReceipt(String receipt_id, String establishment_id,
                                     String price, String date, String score, String master_id) throws SQLException, FileNotFoundException, ClassNotFoundException {
        String summ = getPricebyName(price);
        String priceID = getPriceID(price);
        String estID = getStructure_byNAME(establishment_id);
        statmt.execute("UPDATE public.receipt\n" +
                "\tSET establishment_id='"+ estID+"', price='"+
                summ +"', date='"+ date +"', score='"+ score+"', master_id='"+master_id+"', service_id = '"+priceID+"'\n" +
                "\tWHERE id_receipt ='"+  receipt_id+"';");
    }

    public static String getStructure_byNAME(String name) throws SQLException, ClassNotFoundException, FileNotFoundException {
        statmt = connection.createStatement();
        String t="";
        data_resSet = statmt.executeQuery("SELECT id FROM establishment WHERE establishment_name = '"+ name+"';");
        while (data_resSet.next()) {
            //System.out.println("i:"+i);
            String t1 = data_resSet.getString("id");
            t = t1;
        }
        System.out.println("-");
        return t;
    }
    public static String getService_byNAME(String name) throws SQLException, ClassNotFoundException, FileNotFoundException {
        statmt = connection.createStatement();
        String t="";
        data_resSet = statmt.executeQuery("SELECT id FROM service WHERE name = '"+ name+"';");
        while (data_resSet.next()) {
            //System.out.println("i:"+i);
            String t1 = data_resSet.getString("id");
            t = t1;
        }
        System.out.println("-");
        return t;
    }

    public static String getEmployee_byLogin(String name) throws SQLException, ClassNotFoundException, FileNotFoundException {
        statmt = connection.createStatement();
        String t="";
        data_resSet = statmt.executeQuery("SELECT employee_id FROM users WHERE username = '"+ name+"';");
        while (data_resSet.next()) {
            //System.out.println("i:"+i);
            String t1 = data_resSet.getString("employee_id");
            t = t1;
        }
        System.out.println("-");
        return t;
    }

    public static String getEmployee_byName(String name) throws SQLException, ClassNotFoundException, FileNotFoundException {
        statmt = connection.createStatement();
        String t="";
        data_resSet = statmt.executeQuery("SELECT id FROM employee WHERE full_name = '"+ name+"';");
        while (data_resSet.next()) {
            //System.out.println("i:"+i);
            String t1 = data_resSet.getString("id");
            t = t1;
        }
        System.out.println("-");
        return t;
    }

    public static Client[] findClient_byNAME(String Findname) throws SQLException {
        String k = getLastClient();
        int kol = Integer.parseInt(k);
        Client[] mas = new Client[kol];
        data_resSet = data_statmt.executeQuery("SELECT * FROM client join client_bonus on client.id=client_bonus.client_id \n" +
                "WHERE position('" + Findname + "' in full_name)>0;");
        int i = 0;
        while(data_resSet.next()) {
            String id = data_resSet.getString("id");
            String name = data_resSet.getString("full_name");
            String status = data_resSet.getString("client_status");
            String bonus = data_resSet.getString("bonus");
            mas[i] = new Client(id,name,status,bonus);
            i++;
        }
        return mas;
    }

    public static Visit[] getAllVisit_Staff() throws SQLException, ClassNotFoundException, FileNotFoundException {
        String r = getLastVisit();
        int kol = Integer.parseInt(r);
        Visit[] mas = new Visit[kol];
        data_resSet = statmt.executeQuery("SELECT id_receipt, client.full_name as w, establishment.establishment_name as e, price FROM receipt\n" +
                "JOIN client on client.id = receipt.client_id\n" +
                "JOIN establishment on establishment.id = receipt.establishment_id\n" +
                " ORDER BY id_receipt asc;");
        int i=0;
        while (data_resSet.next()) {
            System.out.println("i:"+i);
            String t1 = data_resSet.getString("id_receipt");
            String t2 = data_resSet.getString("w");
            String t3 = data_resSet.getString("e");
            String t4 = "";
            String t5 = "";
            String t6 = "";
            String t7 = data_resSet.getString("price");
            String t8 = "";
            //System.out.println(t1 + " | " + t2 + " | " + t3 + " | " + t4);
            mas[i] = new Visit(t1,t2,t3,t4,t5,t6,t7,t8);
            i++;
        }
        System.out.println("-");
        return mas;
    }
    public static String getLastVisit() throws SQLException {
        String id;
        data_resSet = statmt.executeQuery("SELECT count(id_receipt) FROM receipt;");
        data_resSet.next();
        id = data_resSet.getString("count");
        //System.out.println("maxID_client: " + id);
        return id;
    }

    public static Visit getReceipt_byID(String id) throws SQLException {
        data_resSet = statmt.executeQuery("SELECT * FROM receipt\n" +
                "JOIN client on client.id = receipt.client_id " +
                "JOIN establishment on establishment.id = receipt.establishment_id\n" +
                "JOIN employee on employee.id = receipt.master_id " +
                "JOIN service on receipt.service_id = service.id " +
                " WHERE id_receipt = '" + id + "';");
        Visit mas = null;
        while (data_resSet.next()) {
            String t1 = data_resSet.getString("id_receipt");
            String t2 = data_resSet.getString("full_name");
            String t3 = data_resSet.getString("establishment_name");
            String t4 = "";
            String t5 = data_resSet.getString("price");
            String t6 = data_resSet.getString("name");
            String t7 = data_resSet.getString("date");
            String t8 = data_resSet.getString("score");
            String t9 = data_resSet.getString("master_id");
            mas = new Visit(t1,t2,t3,t6,t7,t9,t5,t8);
        }
        return mas;
    }
    public static void delBooking(String id) throws SQLException {
        statmt.execute("DELETE FROM receipt_booking where id_booking ='"+ id+"';");
        statmt.execute("DELETE FROM booking where id_booking = '"+ id+"';");
    }
    public static void delBooking2(String id) throws SQLException {
        statmt.execute("DELETE FROM receipt_booking where id_receipt ='"+ id+"';");
        statmt.execute("DELETE FROM receipt where id_receipt = '"+ id+"';");
    }

    public static String getClientName_byID(String id) throws SQLException {
        String name="";
        data_resSet = statmt.executeQuery("SElect * from client where id ='"+id+"';");
        while (data_resSet.next()){
            name = data_resSet.getString("client_name");
        }
        return name;
    }
    public static void deleteReceipt(String id) throws SQLException {
        statmt.execute("DELETE FROM public.receipt\n" +
                "\tWHERE id_receipt = '"+ id+"';");
    }


    public static void UpdateVisit(String visit_id, String client_id, String str, String ser, String date,
                                   String emp,String summa, String points) throws SQLException, FileNotFoundException, ClassNotFoundException {
        str = Postgre.getStructure_byNAME(str);
        ser = Postgre.getService_byNAME(ser);
        // System.out.println(emp);
        emp = Postgre.getEmployee_byName(emp);
        //System.out.println(str+"|"+ ser+"|"+"|"+date+"|"+emp);
        statmt.execute("UPDATE visit " +
                "set client_id = "  + client_id + "," +
                " structure_id = "+ str + "," +
                " service_id = " + ser + "," +
                " data = '" + date + "'," +
                " employee_id = " + emp + "," +
                " summa = " + summa + "," +
                " points = " + points +
                " where visit_id = " + visit_id+";");
    }
    public static void delVisit(String t1) throws SQLException {
        statmt.execute("DELETE FROM visit "+
                " where visit_id = " + t1+";");
    }

    public static void task1() throws SQLException {
        statmt.execute("COPY (SELECT sum(receipt.price) as \"Доход от услуги\", name as \"Название услуги\"\n" +
                "\t  FROM receipt\n" +
                "\t  JOIN service on receipt.service_id = service.id\n" +
                "\t  GROUP BY \"Название услуги\"\n" +
                "\t  ORDER BY \"Доход от услуги\" desc\n" +
                "\t  LIMIT 3\n" +
                "\t  )\n" +
                "\t TO 'D:\\max_service.csv' CSV HEADER\n" +
                "\t DELIMITER '|' ENCODING 'WIN1251';");
    }

    public static void task2() throws SQLException {
        statmt.execute("COPY (SELECT sum(receipt.price) as \"Доход от услуги\", name as \"Название услуги\"\n" +
                "\t  FROM receipt\n" +
                "\t  JOIN service on service.id = receipt.service_id\n" +
                "\t  GROUP BY \"Название услуги\"\n" +
                "\t  ORDER BY \"Доход от услуги\" asc\n" +
                "\t  LIMIT 3\n" +
                "\t  )\n" +
                "\t TO 'D:\\min_service.csv' CSV HEADER\n" +
                "\t DELIMITER '|' ENCODING 'WIN1251';");
    }
    public static void task5() throws SQLException {
        statmt.execute("COPY (SELECT full_name, rating\n" +
                "\t  FROM employee\n" +
                "\t  ORDER BY rating DESC\n" +
                "\t  LIMIT 3\n" +
                "\t )\n" +
                "\t TO 'D:\\avg_employee.csv' CSV HEADER\n" +
                "\t DELIMITER '|' ENCODING 'WIN1251';");
    }

    public static Service[] getService() throws SQLException, ClassNotFoundException, FileNotFoundException {
        String r = getLastService();
        int kol = Integer.parseInt(r);
        Service[] mas = new Service[kol];
        data_resSet = data_statmt.executeQuery("SELECT * FROM service ORDER by id asc;");
        int i=0;
        while (data_resSet.next()) {
            String t1 = data_resSet.getString("id");
            String t2 = data_resSet.getString("name");
            String t3 = data_resSet.getString("price");
            mas[i] = new Service(t1,t2,t3);
            i++;
        }
        return mas;
    }
    public static String[] getService_name() throws SQLException {
        String r = getLastService();
        int kol = Integer.parseInt(r);
        String[] mas = new String[kol];
        data_resSet = data_statmt.executeQuery("SELECT * FROM service ORDER by id asc;");
        int i=0;
        while (data_resSet.next()) {
            String t1 = data_resSet.getString("id");
            String t2 = data_resSet.getString("name");
            String t3 = data_resSet.getString("price");
            mas[i] = t2;
            i++;
        }
        return mas;
    }


    public static void addWork(String w1, String w2) throws SQLException, FileNotFoundException, ClassNotFoundException {
        w2 = getStructure_byNAME(w2);
        w1 = getEmployee_byName(w1);
        statmt.execute("INSERT INTO public.establishment_employee(\n" +
                "\testablishment_id, employee_id)\n" +
                "\tVALUES ('"+ w2+"', '"+w1+"');");
    }

}
