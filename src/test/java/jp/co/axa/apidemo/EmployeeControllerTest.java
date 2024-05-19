package jp.co.axa.apidemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetEmployees() throws Exception {
        Employee employee1 = new Employee(1L, "Mark", 50000, "Engineering");
        Employee employee2 = new Employee(2L, "Micky", 60000, "Marketing");

        Mockito.when(employeeService.retrieveEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        mockMvc.perform(get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Mark"))
                .andExpect(jsonPath("$[0].salary").value(50000))
                .andExpect(jsonPath("$[0].department").value("Engineering"))
                .andExpect(jsonPath("$[1].name").value("Micky"))
                .andExpect(jsonPath("$[1].salary").value(60000))
                .andExpect(jsonPath("$[1].department").value("Marketing"))
                .andDo(print());
    }

    @Test
    public void testGetEmployee() throws Exception {
        Employee employee = new Employee(1L, "Mark", 50000, "Engineering");

        Mockito.when(employeeService.getEmployee(anyLong())).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mark"))
                .andExpect(jsonPath("$.salary").value(50000))
                .andExpect(jsonPath("$.department").value("Engineering"))
                .andDo(print());
    }

    @Test
    public void testSaveEmployee() throws Exception {
        Employee employee = new Employee(null, "Mark", 50000, "Engineering");

        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(1)).saveEmployee(any(Employee.class));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Mockito.doNothing().when(employeeService).deleteEmployee(anyLong());

        mockMvc.perform(delete("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(1)).deleteEmployee(anyLong());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Employee employee = new Employee(1L, "Mark", 50000, "Engineering");

        Mockito.when(employeeService.getEmployee(anyLong())).thenReturn(employee);
        Mockito.doNothing().when(employeeService).updateEmployee(any(Employee.class));

        mockMvc.perform(put("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(employeeService, Mockito.times(1)).updateEmployee(any(Employee.class));
    }
    
    @Test
    public void testSearchEmployees() throws Exception {
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Mark");
        employee1.setSalary(50000);
        employee1.setDepartment("Engineering");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Micky");
        employee2.setSalary(60000);
        employee2.setDepartment("Marketing");

        Mockito.when(employeeService.searchEmployees("Mark", null, null))
                .thenReturn(Arrays.asList(employee1));
        Mockito.when(employeeService.searchEmployees(null, 60000, null))
                .thenReturn(Arrays.asList(employee2));
        Mockito.when(employeeService.searchEmployees(null, null, "Engineering"))
                .thenReturn(Arrays.asList(employee1));
        Mockito.when(employeeService.searchEmployees(null, null, null))
                .thenReturn(Arrays.asList(employee1, employee2));

        mockMvc.perform(get("/api/v1/employees/search")
                .param("name", "Mark")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Mark"))
                .andExpect(jsonPath("$[0].salary").value(50000))
                .andExpect(jsonPath("$[0].department").value("Engineering"));

        mockMvc.perform(get("/api/v1/employees/search")
                .param("salary", "60000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Micky"))
                .andExpect(jsonPath("$[0].salary").value(60000))
                .andExpect(jsonPath("$[0].department").value("Marketing"));

        mockMvc.perform(get("/api/v1/employees/search")
                .param("department", "Engineering")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Mark"))
                .andExpect(jsonPath("$[0].salary").value(50000))
                .andExpect(jsonPath("$[0].department").value("Engineering"));

        mockMvc.perform(get("/api/v1/employees/search")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
