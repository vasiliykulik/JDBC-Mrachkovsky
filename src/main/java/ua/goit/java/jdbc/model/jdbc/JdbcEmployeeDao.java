package ua.goit.java.jdbc.model.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.java.jdbc.model.Employee;
import ua.goit.java.jdbc.model.EmployeeDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcEmployeeDao implements EmployeeDao {
/*1. так у нас try with resources, то соответственно мы по выходе из блока закроем Connection, и по этому хотелось что бы этот connection:
нам кто то бы предоставлял, что бы у нас был какой то класс, bean - мы ему один раз передадим эти параметры, и он передавал этот Connection
иметь возможность хранить pool connection, в данный момент активных - что бы могли воспользоваться уже открытыми connectionами
класс в Java в JDBC пакете DataSource (javax.sql) Factory class для получения Connection*/
    private DataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);
    private String url = "jdbc:postgresql://localhost:5432/company";
    private String user = "user";
    private String password = "111";

    public JdbcEmployeeDao() {
        loadDriver();
    }

    @Override
    public Employee load(int id) {

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT *FROM EMPLOYEE WHERE ID = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createEmployee(resultSet);
            } else {

                throw new RuntimeException("Cannot find ua.goit.java.jdbc.model.Employee with id " + id);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception ocurred while connecting to DB" + url, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPLOYEE ");
            while (resultSet.next()) {
                Employee employee = createEmployee(resultSet);
                result.add(employee);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception ocurred while connecting to DB" + url, e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private Employee createEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("ID"));
        employee.setName(resultSet.getString("NAME"));
        employee.setAge(resultSet.getInt("AGE"));
        employee.setAddress(resultSet.getString("ADDRESS"));
        employee.setSalary(resultSet.getFloat("Salary"));
        employee.setJoinDate(resultSet.getString("JOIN_DATE"));
        return employee;
    }

    private void loadDriver() {
        try {
            LOGGER.info("Loading JDBC driver: org.postgresql.Driver");
            Class.forName("org.postgresql.Driver");
            LOGGER.info("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Cannot find driver: org.postgresql.Driver");
            throw new RuntimeException(e);
        }
    }
}