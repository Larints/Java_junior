package seminar_3;

import java.sql.*;

public class JDBC {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbcstudent";
        String user = "root";
        String password = "Njvfc1991!";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
//            createTable(connection);
//            insertInto(connection);
            System.out.println(simpleSelect(connection));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private static String simpleSelect(Connection connection) {
        StringBuilder sb = new StringBuilder();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT * FROM Students
                      """);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                int age = resultSet.getInt("age");
                sb.append(String.format("Id: %d, Name: %s, Second Name: %s, Age: %d", id, name, secondName, age));
                return sb.toString();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static void insertInto(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate("""
                    INSERT INTO Students(id, first_name, second_name, age)
                    VALUES('1', 'Liana', 'States', 32)
                      """);
            System.out.println(rowsAffected);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table Students(
                    id int,
                    first_name varchar(256),
                    second_name varchar(256),
                    age int
                    )
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
