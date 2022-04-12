package org.forum.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBC {

    public static void main(String[] args) {


        String jdbcUrl = "jdbc:mysql://iis-forum-db-do-user-8375878-0.b.db.ondigitalocean.com:25060/forum?ssl-mode=REQUIRED";
        String user = "doadmin";
        String password = "lj8tpe23c7yv5h05";

        try {

            System.out.println("Connecting to database " + jdbcUrl);
            Connection myConn = DriverManager.getConnection(jdbcUrl, user, password);

            System.out.println("Connection successful!!!");
        }

        catch (Exception exc) {
            exc.printStackTrace();
        }
    }


}
