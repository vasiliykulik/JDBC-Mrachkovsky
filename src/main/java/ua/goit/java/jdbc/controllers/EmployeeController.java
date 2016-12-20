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
/*    public List<Employee> getAllEmployees() {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        try {
            List<Employee> result = employeeDao.findAll();
            txManager.commit(status);
            return result;
        } catch (Exception e) {
            txManager.rollback(status);
            throw new RuntimeException(e);// и передадим e в конструктор
        }
    }*/
// Вынос кода транзакции в Аспекты
    // 63. Для того чтобы исправить - разкоментим
    // 62. Изменим getAllEmployeeById (EmployeeController) - сделаем не Transactional

    // 70. какими проблемами столкнемся, какое поведение программы в контексте открытия транзакции
    // Вызов Транзакционного метода из нетранзакционного в рамках одного класса
    // 71. (Этот метод не Transactional)
   /*
    75. Когда вы вызываете методы из самого класса - вы вызываете непосредственно на том объекте, а не на
    proxy, внутри которого есть Advice, который как раз и перенаправляет вызов метода к аспекту, который вызывают Транзакцию
    Transaction в Spring построен на основе АОП
    Вот эта Аннотация просто говорит Spring, когда вы создаете bean,
    он создаст для него proxy и здесь вставит Advice, который вызовет Аспект, который откроет вам
    Транзакцию при входе в этот метод и закроет ее при выходе из этого метода, если это необходимо*/

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll(); //74. то при вызове этого метода транзакция еще не была открыта

    }
    // 71. для наглядности вот таким странным образм перебираем всех (Этот метод не Transactional)
    /*
    76.
    Здесь же получается:
    что мы входим в этот метод, транзакция не открывается, EmployeeController ничего не знает
    о существовании proxy в который он завернут*/

    public Employee getAllEmployeeById(int id){
        List<Employee> employees = getAllEmployees();
        for (Employee employee:employees){
            if(employee.getId() == id){
                return employee;
            }
        }
        return null;
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
