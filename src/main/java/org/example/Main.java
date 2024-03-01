package org.example;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class Main {
    static DataSource dataSource = createDataSource();

    private static DataSource createDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:user.sql'");
        return ds;
    }

    public static void main(String[] args) {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Connection established: " + connection.isValid(0));
            // CRUD
            // Get
            PreparedStatement ps = connection.prepareStatement("select * from USERS where name = ?");
            ps.setString(1, "John");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                System.out.println("Id: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("name"));
            }
            // Create
            PreparedStatement insertPs = connection.prepareStatement("insert into USERS (name) values (?)");
            insertPs.setString(1, "John");
            int insertCount = insertPs.executeUpdate();
            System.out.println("Insert count: " + insertCount);
            // update
            PreparedStatement updatePs = connection.prepareStatement("update USERS set name = ? where name = ?");
            updatePs.setString(1, "Johnny");
            updatePs.setString(2, "John");
            int updateCount = updatePs.executeUpdate();
            System.out.println("update count: " + updateCount);
            // delete
            PreparedStatement deletePs = connection.prepareStatement("delete USERS where name = ?");
            deletePs.setString(1, "Johnny");
            int deleteCount = deletePs.executeUpdate();
            System.out.println("delete count: " + deleteCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}