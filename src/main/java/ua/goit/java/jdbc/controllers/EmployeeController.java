package ua.goit.java.jdbc.controllers;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ua.goit.java.jdbc.model.Employee;
import ua.goit.java.jdbc.model.EmployeeDao;

import java.util.List;

/**
 * Created by Стрела on 06.12.2016.
 */
public class EmployeeController {
    // 2. с помощью которого будем открывать транзакцию и закрывать транзакцию
    private PlatformTransactionManager txManager;
    private EmployeeDao employeeDao;

    // 1. будет поддержка транзакций
    public List<Employee> getAllEmployees() {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            List<Employee> result = employeeDao.findAll();
            txManager.commit(status);
            return result;
        } catch (Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);// и передадим e в конструктор
        }
    }
// Вынос кода транзакции в Аспекты
    @Transactional(propagation = Propagation.REQUIRED)
    public Employee getAllEmployeeById(int id) {
        return employeeDao.load(id);
    }

/*    // Вынос кода транзакции в Аспекты
    @Transactional
    public Employee getAllEmployeeById(int id) {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            Employee result = employeeDao.load(id);
            txManager.commit(status);
            return result;
        } catch (Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);// и передадим e в конструктор
        }
    }*/
    /* У нас есть наш Controller, давайте объявим его как bean, чтобы мы могли с ним работать application-context.xml:
     В EmployeeController добавим setter*/
    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
}
