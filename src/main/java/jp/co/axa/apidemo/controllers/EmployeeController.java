package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.EmployeeNotFoundException;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * REST controller for managing employees.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
  
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    /**
     * Retrieves all employees.
     * 
     * @return a list of employees
     */
    
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return employees;
    }
    /**
     * Retrieves an employee by their ID.
     * 
     * @param employeeId the ID of the employee to retrieve
     * @return the employee with the specified ID
     */
    
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    /**
     * Saves a new employee.
     * 
     * @param employee the employee to save
     */
    @PostMapping("/employees") 
    public void saveEmployee(@RequestBody Employee employee){
        employeeService.saveEmployee(employee);
        System.out.println("Employee Saved Successfully");
    }

    /**
     * Deletes an employee by their ID.
     * 
     * @param employeeId the ID of the employee to delete
     */
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        employeeService.deleteEmployee(employeeId);
        System.out.println("Employee Deleted Successfully");
    }
    
    /**
     * Updates an existing employee.
     * 
     * @param employee the employee data to update
     * @param employeeId the ID of the employee to update
     */
    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId){
    	 try {
             Employee existingEmployee = employeeService.getEmployee(employeeId);
             if (existingEmployee != null) {
                 employeeService.updateEmployee(employee);
                 System.out.println("Employee Updated Successfully");
             }
         } catch (EmployeeNotFoundException e) {
             System.out.println(e.getMessage());
             throw e;
         }

    }
    
    /**
     * Search for employees by name, salary, and department.
     * @param name Name of the employee.
     * @param salary Salary of the employee.
     * @param department Department of the employee.
     * @return List of employees matching the search criteria.
     */
    
    @GetMapping("/employees/search")
    public List<Employee> searchEmployees(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) Integer salary,
                                          @RequestParam(required = false) String department) {
        return employeeService.searchEmployees(name, salary, department);
    }   

}
