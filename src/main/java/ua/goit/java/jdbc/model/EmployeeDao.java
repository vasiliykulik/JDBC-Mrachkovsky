package ua.goit.java.jdbc.model;

import java.util.List;

/**
 * Created by Стрела on 03.12.2016.
 */
public interface EmployeeDao {
    // 31. Prepared Statement для динамического query
    Employee load(int id);

    // 13. Метод который возвращает всех ua.goit.java.jdbc.model.Employee c нашей таблички
    List<Employee> getAll();
}
