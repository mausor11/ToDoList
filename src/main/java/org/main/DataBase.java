package org.main;

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
    public void insertTask(String table, String task) {
        String sql = "INSERT INTO [" + table + "](task, done) VALUES (?, ?)";
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
        String sql = "CREATE TABLE [" + list + "](task TEXT(100), done INT DEFAULT 0)";
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteTables(ArrayList<String> tables) {
        for(String table : tables) {
            String sql = "DROP TABLE [" + table + "]";
            try {
                Statement statement = connection.createStatement();
                statement.execute(sql);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void doneTask(String table, String doneTask) {
        String sql = "UPDATE [" + table +  "] SET done = '1' WHERE task = \'" + doneTask + "'";
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteAll(String table) {
        String sql = "DELETE FROM [" + table + "]";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getAll(String table) {
        try {
            ArrayList<String> tasks = new ArrayList<>();
            String sql = "SELECT * FROM [" + table + "]";
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
    public ArrayList<String> getOnlyDone(String table) {
        try {
            ArrayList<String> tasks = new ArrayList<>();
            String sql = "SELECT t.* FROM [" + table + "] t WHERE t.done = 1";
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
    public ArrayList<String> getOnlyWaiting(String table) {
        try {
            ArrayList<String> tasks = new ArrayList<>();
            String sql = "SELECT t.* FROM [" + table + "] t WHERE t.done = 0";
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
    public static void main(String[] args) throws SQLException {
        DataBase dataBase = new DataBase();
        dataBase.createNewList("table1");
    }
}
