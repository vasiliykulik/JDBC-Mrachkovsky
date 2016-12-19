package ua.goit.java.jdbc.controllers;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ua.goit.java.jdbc.model.Employee;

import java.util.List;

/**
 * Created by Стрела on 06.12.2016.
 */
public class EmployeeController {
// 2. с помощью которого будем открывать транзакцию и закрывать транзакцию
    private PlatformTransactionManager txManager;
    // 1. будет поддержка транзакций
    public List<Employee> getAllEmployees(){

        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            List<Employee> result = employeeDao.
        }

    }
}
