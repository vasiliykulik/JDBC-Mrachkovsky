package ua.goit.java.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.goit.java.jdbc.controllers.EmployeeController;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private EmployeeController employeeController;

    public static void main(String[] args) {
        // В Main инициализируем наш Context
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Main main = context.getBean(Main.class);
        main.start();


        /*В Main инициализируем наш Context, и это уже нам не нужно
        EmployeeDao jdbcEmployeeDao = new JdbcEmployeeDao();
        System.out.println("All ua.goit.java.jdbc.model.Employee");
        jdbcEmployeeDao.findAll().forEach(System.out::println);
        System.out.println("ua.goit.java.jdbc.model.Employee with id 3");
        System.out.println(jdbcEmployeeDao.load(3));*/
    }

    private void start() {
        employeeController.getAllEmployees().forEach(System.out::println);
    }

    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }
}
