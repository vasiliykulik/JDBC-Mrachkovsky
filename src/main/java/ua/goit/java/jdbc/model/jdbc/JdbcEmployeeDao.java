package ua.goit.java.jdbc.model.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
/*  Теперь Наш код не зависит от того каким DataSource мы пользуемся.
    private String url = "jdbc:postgresql://localhost:5432/company";
    private String user = "user";
    private String password = "111";
    public JdbcEmployeeDao() {
        loadDriver();
    }*/


    @Override
    // 60. Dao - это объекты непосредственно работают с БД - и когда вы обращаетесь с ServiceLayer транзакция
    // уже должна быть подключена (не всегда это нужно, и сейчас EmployeeController - выглядет лишней сущностью)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Employee load(int id) {
       /* Не нужно каждому классу, каждому Dao знать о Имени пользователя о password, кто будет коннектится к БД*/
        try (Connection connection = dataSource.getConnection();
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
            LOGGER.error("Exception ocurred while connecting to DB" + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPLOYEE ");
            while (resultSet.next()) {
                Employee employee = createEmployee(resultSet);
                result.add(employee);
            }
        } catch (SQLException e) {
            LOGGER.error("Exception ocurred while connecting to DB" + e);
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


    // в JdbcEmployeeDao  добавим setter Datasource
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}