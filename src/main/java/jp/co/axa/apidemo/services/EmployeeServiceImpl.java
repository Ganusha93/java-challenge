package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing employees.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	/**
	 * Retrieves an employee by their ID.
	 * 
	 * @param employeeId the ID of the employee to retrieve
	 * @return the employee with the specified ID
	 */
	@Cacheable("employees")
	public List<Employee> retrieveEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		return employees;
	}

	/**
	 * Retrieves an employee by their ID.
	 * 
	 * @param employeeId the ID of the employee to retrieve
	 * @return the employee with the specified ID
	 */
	@Cacheable(value = "employees", key = "#employeeId")
	public Employee getEmployee(Long employeeId) {
		Optional<Employee> optEmp = employeeRepository.findById(employeeId);
		if (optEmp.isPresent()) {
			return optEmp.get();
		} else {
			throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
		}
	}

	/**
	 * Saves a new employee.
	 * 
	 * @param employee the employee to save
	 */
	@CachePut(value = "employees", key = "#employee.id")
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	/**
	 * Deletes an employee by their ID.
	 * 
	 * @param employeeId the ID of the employee to delete
	 */
	@CacheEvict(value = "employees", key = "#employeeId")
	public void deleteEmployee(Long employeeId) {
		if (employeeRepository.existsById(employeeId)) {
			employeeRepository.deleteById(employeeId);
		} else {
			throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
		}
	}

	/**
	 * Updates an existing employee.
	 * 
	 * @param employee the employee data to update
	 */
	@CachePut(value = "employees", key = "#employee.id")
	public void updateEmployee(Employee employee) {
		if (employeeRepository.existsById(employee.getId())) {
			employeeRepository.save(employee);
		} else {
			throw new EmployeeNotFoundException("Employee with ID " + employee.getId() + " not found.");
		}
	}

	/**
	 * Search for employees by name, salary, and department.
	 * 
	 * @param name       Name of the employee.
	 * @param salary     Salary of the employee.
	 * @param department Department of the employee.
	 * @return List of employees matching the search criteria.
	 */
	@Override
	@Cacheable("employees")
	public List<Employee> searchEmployees(String name, Integer salary, String department) {
		return employeeRepository.searchEmployees(name, salary, department);
	}

}