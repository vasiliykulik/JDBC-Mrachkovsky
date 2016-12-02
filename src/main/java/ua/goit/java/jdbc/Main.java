package ua.goit.java.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.java.jdbc.model.jdbc.JdbcEmployeeDao;
import ua.goit.java.jdbc.model.EmployeeDao;


/**
 * Created by Стрела on 30.11.2016.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // 30. B теперь в ua.goit.java.jdbc.Main создадим
        EmployeeDao jdbcEmployeeDao = new JdbcEmployeeDao();
        System.out.println("All ua.goit.java.jdbc.model.Employee");
        jdbcEmployeeDao.getAll().forEach(System.out::println);
        // 41. теперь проверим PreparedStatement
        System.out.println("ua.goit.java.jdbc.model.Employee with id 3");
        System.out.println(jdbcEmployeeDao.load(3));
    }
}
