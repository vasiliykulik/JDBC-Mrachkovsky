import java.time.LocalDateTime;

/**
 * 8.Сделаем более ООП
 * создадим класс Employee
 */
public class Employee {
    private int id;
    private String name;
    private int age;
    private String address;
    private float salary;
    private String joinDate;

    //9. Нагенерим геттеров и сеттеров, и tostring


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", address='").append(address).append('\'');
        sb.append(", salary=").append(salary);
        sb.append(", joinDate='").append(joinDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

}
