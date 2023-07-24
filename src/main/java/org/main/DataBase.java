package org.main;


import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {
    private Connection connection;
    public DataBase() {
        String databaseURL = "jdbc:ucanaccess://DataBase.accdb";
        try {
            this.connection = DriverManager.getConnection(databaseURL);
            System.out.println("Connected.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void endConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertTask(String task) {
        String sql = "INSERT INTO toDoList(task, done) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, task);
            preparedStatement.setString(2, "0");
            int row = preparedStatement.executeUpdate();
            if(row > 0) {
                System.out.println("Successfully added.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createNewList(String list) {
        String sql = "CREATE TABLE " + list + "(id_list INT PRIMARY KEY, task TEXT(100), done INT DEFAULT 0)";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void doneTask(String doneTask) {
        String sql = "UPDATE toDoList SET done = '1' WHERE task = \'" + doneTask + "'";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteAll() {
        String sql = "DELETE FROM toDoList";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getAll() {
        try {
            ArrayList<String> tasks = new ArrayList<>();
            String sql = "SELECT * FROM toDoList";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                tasks.add(resultSet.getString("task"));
            }
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getOnlyDone() {
        try {
            ArrayList<String> tasks = new ArrayList<>();
            String sql = "SELECT t.* FROM toDoList t WHERE t.done = 1";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                tasks.add(resultSet.getString("task"));
            }
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getOnlyWaiting() {
        try {
            ArrayList<String> tasks = new ArrayList<>();
            String sql = "SELECT t.* FROM toDoList t WHERE t.done = 0";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                tasks.add(resultSet.getString("task"));
            }
            return tasks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getAllTables() {
        String sql = "SELECT * FROM information_schema.tables";
        try (ResultSet rs = connection.getMetaData().getTables(null, null, null, null)) {
            ArrayList<String> tables = new ArrayList<>();
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        DataBase dataBase = new DataBase();
        dataBase.getAllTables();
    }
}
