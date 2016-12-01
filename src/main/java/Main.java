import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;


/**
 * Created by Стрела on 30.11.2016.
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        loadDriver();
        // 4. Залоггируем, уже лучше чем то что было
        LOGGER.info("Connecting to DB");
        // 6. Вынесем в переменные
        String url = "jdbc:postgresql://localhost:5432/company";
        String user = "user";
        String password = "111";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             // 7. Успешно законектившись попросим statement что бы выполнить запрос
             Statement statement = connection.createStatement()) {

            // 4. Залоггируем, уже лучше чем то что было
            LOGGER.info("Successfully connected to DB");

            String sql = "Select * From EMPLOYEE ";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("ID"));
                employee.setName(resultSet.getString("NAME"));
                employee.setAge(resultSet.getInt("AGE"));
                employee.setAddress(resultSet.getString("ADDRESS"));
                employee.setSalary(resultSet.getFloat("Salary"));
                // 10.getDate возвращает sql дату - и нам надо сконвертировать в Java дату, это делается таким образом
                employee.setJoinDate(resultSet.getString("JOIN_DATE"));
                //11. выведем наших Employee на экран
                System.out.println(employee.toString());
            }


        } catch (SQLException e) {
            // 5. заменим e.printStackTrace() на. Еще и заллогируем сам Exception;
            LOGGER.error("Exception ocurred while connecting to DB" + url, e);
        }
    }

    // 1. вручную вызовем драйвер
    private static void loadDriver() {
        try {
            // 2. залоггируем что мы грузим драйвер
            LOGGER.info("Loading JDBC driver: org.postgresql.Driver");
            Class.forName("org.postgresql.Driver");
            // 3. если этот метод exception не кинул - то мы залоггируем - что успешно
            LOGGER.info("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Cannot find driver: org.postgresql.Driver");
            throw new RuntimeException(e);
        }
    }
}
