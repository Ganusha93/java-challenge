package jp.co.axa.apidemo.entities;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name="EMPLOYEE")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="EMPLOYEE_NAME")
    private String name;


    @Column(name="EMPLOYEE_SALARY")
    private Integer salary;

    @Column(name="DEPARTMENT")
    private String department;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Employee() {}

    public Employee(Long id, String name, Integer salary, String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }
}
