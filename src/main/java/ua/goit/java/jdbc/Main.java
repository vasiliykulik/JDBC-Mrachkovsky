package ua.goit.java.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.java.jdbc.model.EmployeeDao;
import ua.goit.java.jdbc.model.jdbc.JdbcEmployeeDao;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        EmployeeDao jdbcEmployeeDao = new JdbcEmployeeDao();
        System.out.println("All ua.goit.java.jdbc.model.Employee");
        jdbcEmployeeDao.getAll().forEach(System.out::println);
        System.out.println("ua.goit.java.jdbc.model.Employee with id 3");
        System.out.println(jdbcEmployeeDao.load(3));
    }
}
