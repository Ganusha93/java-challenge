package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	@Query("SELECT e FROM Employee e WHERE " +
            "(:name IS NULL OR e.name = :name) AND " +
            "(:salary IS NULL OR e.salary = :salary) AND " +
            "(:department IS NULL OR e.department = :department)")
    List<Employee> searchEmployees(@Param("name") String name, 
                                   @Param("salary") Integer salary, 
                                   @Param("department") String department);
}
