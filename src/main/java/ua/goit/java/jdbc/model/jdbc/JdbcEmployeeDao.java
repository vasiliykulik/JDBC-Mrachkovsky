package ua.goit.java.jdbc.model.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.java.jdbc.model.Employee;
import ua.goit.java.jdbc.model.EmployeeDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Стрела on 02.12.2016.
 */
// 12. Создадим Dao и попробуем имплементировать несколько методов
public class JdbcEmployeeDao implements EmployeeDao {
    // 15. чего у нас нет - так это LOGGER - создадим
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);
    // 21. и тут же провести инициализацию
    private String url = "jdbc:postgresql://localhost:5432/company";
    private String user = "user";
    private String password = "111";

    // 22. loadDriver() засунем в конструктор, что бы не загружать его каждый раз
    public JdbcEmployeeDao() {
        loadDriver();
    }

    // 31. Prepared Statement для динамического query
    @Override public Employee load(int id) {
        // 33. теперь создаем ua.goit.java.jdbc.model.Employee
        // 34. теперь мы можем сделать немного по другому  ua.goit.java.jdbc.model.Employee employee = null;
        // 32. откроем connection
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT *FROM EMPLOYEE WHERE ID = ?")) {
            // 40. Да, еще, preparedStatment мы создали - нам нужно как то передать параметры в prepared statment.
            // 40. Индекс и сам параметр - под знаком вопроса - под индексом 1(AND был бы под индексом 2)
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            // 35.
            if (resultSet.next()) {
                // 37. new ua.goit.java.jdbc.model.Employee() заменим на вызов метода createEmployee(resultSet)
                // 38. переменная ua.goit.java.jdbc.model.Employee result не нужна - просто return
                return createEmployee(resultSet);
            } else {
                // 39. Если объектов несколько - мы их игнорим - что не должно быть
                // - можем добавить проверочку и на это
                throw new RuntimeException("Cannot find ua.goit.java.jdbc.model.Employee with id " + id);
            }
            // 33. добавим catch clause
        } catch (SQLException e) {
            LOGGER.error("Exception ocurred while connecting to DB" + url, e);
            throw new RuntimeException(e);
        }
    }

    // 13. Метод который возвращает всех ua.goit.java.jdbc.model.Employee c нашей таблички
    @Override public List<Employee> getAll() {
        // 25. Сделаем List
        List<Employee> result = new ArrayList<>();
        // 14. Скопипастим с ua.goit.java.jdbc.Main наш код

        // 4. Залоггируем, уже лучше чем то что было
        // 19. можно и не писать больше LOGGER.info("Connecting to DB");
        // 6. Вынесем в переменные
        // 20. url, user, password - можно вынести в поля CTRL ALT F
        /*url = "jdbc:postgresql://localhost:5432/company";
        user = "user";
        password = "111";*/

        try (Connection connection = DriverManager.getConnection(url, user, password);
             // 7. Успешно законектившись попросим statement что бы выполнить запрос
             Statement statement = connection.createStatement()) {

            // 4. Залоггируем, уже лучше чем то что было
            // 23. LOGGER.info("Successfully connected to DB"); // мы тоже не будем делать
            // 24. Заинлайним CTRL ALT N, что бы оно нам меньше места занимало
            // 24. String sql = "Select * From EMPLOYEE ";
            // 24. ResultSet resultSet = statement.executeQuery(sql); // что бы было компактней

            ResultSet resultSet = statement.executeQuery("Select * From EMPLOYEE ");

            while (resultSet.next()) {
                // 36. создание объекта и запись параметров из БД можно вынести в метод
                Employee employee = createEmployee(resultSet);
                // 11. выведем наших ua.goit.java.jdbc.model.Employee на экран
                // 26. B здесь вместо System.out.println(employee.toString());
                result.add(employee);
            }
        } catch (SQLException e) {
            // 5. заменим e.printStackTrace() на. Еще и заллогируем сам Exception;
            LOGGER.error("Exception ocurred while connecting to DB" + url, e);
            // 28. У нас get метод который не бросает никаких Exception, по хорошему надо пробросить
            // какой то runtimeException что бы не нагружать клиентский код отлавливанием Exception
            throw new RuntimeException(e);
        }
        // 18. Нет return statement
        // 27. И здесь вернем result
        return result;
    }

    private Employee createEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("ID"));
        employee.setName(resultSet.getString("NAME"));
        employee.setAge(resultSet.getInt("AGE"));
        employee.setAddress(resultSet.getString("ADDRESS"));
        employee.setSalary(resultSet.getFloat("Salary"));
        // 10.getDate возвращает sql дату - и нам надо сконвертировать в Java дату, это делается таким образом
        employee.setJoinDate(resultSet.getString("JOIN_DATE"));
        return employee;
    }

    // 16. и еще перетащим метод loadDriver()
    // 1. вручную вызовем драйвер
    // 17. он у нас не static должен быть
    private void loadDriver() {
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