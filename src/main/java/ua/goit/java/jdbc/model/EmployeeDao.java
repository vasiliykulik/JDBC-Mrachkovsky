package ua.goit.java.jdbc.model;

import java.util.List;

public interface EmployeeDao {

    Employee load(int id);

    List<Employee> findAll();
}
