package com.ntapia.callcenter.employee;

import com.ntapia.callcenter.common.TestUtil;

import org.junit.Test;

import java.util.Optional;

import static com.ntapia.callcenter.common.TestUtil.NAME_THANOS;
import static com.ntapia.callcenter.employee.EmployeeStatus.INACTIVE;
import static com.ntapia.callcenter.employee.EmployeeType.OPERARTOR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
public class EmployeeDAOMemoryImplTest{

    @Test
    public void testCreateMustHaveNewEmployee() {
        EmployeeDAO employeeDAO = new EmployeeDAOMemoryImpl();
        Employee employee = employeeDAO.create(TestUtil.buildEmployee(OPERARTOR, INACTIVE));

        assertNotNull(employee);
        assertNotNull(employee.getId());
        assertEquals(OPERARTOR, employee.getEmployeeType());
        assertEquals(INACTIVE, employee.getEmployeeStatus());
    }

    @Test
    public void testUpdateMustHaveChanges() {
        EmployeeDAO employeeDAO = new EmployeeDAOMemoryImpl();
        Employee oldEmployee = employeeDAO.create(TestUtil.buildEmployee(OPERARTOR, INACTIVE));

        Employee actualEmployee = Employee.builder()
                .employeeStatus(oldEmployee.getEmployeeStatus())
                .employeeType(oldEmployee.getEmployeeType())
                .callCounter(oldEmployee.getCallCounter())
                .name(oldEmployee.getName())
                .id(oldEmployee.getId())
                .build();

        actualEmployee.setCallCounter(10L);
        actualEmployee.setName(NAME_THANOS);
        actualEmployee = employeeDAO.update(actualEmployee);

        assertNotNull(actualEmployee);
        assertNotEquals(oldEmployee.getName(), actualEmployee.getName());
        assertNotEquals(oldEmployee.getCallCounter(), actualEmployee.getCallCounter());
        assertEquals(oldEmployee.getId(), actualEmployee.getId());
    }

    @Test
    public void testGetByTypeAndStatusAndMinorCallCounterMustNotFoundOperator() {
        EmployeeDAO employeeDAO = new EmployeeDAOMemoryImpl();

        TestUtil.bulkBuilderDirector(1, 5).forEach(employeeDAO::create);
        TestUtil.bulkBuilderSupervisor(3, 7).forEach(employeeDAO::create);

        Optional<Employee> actual =
                employeeDAO.getByTypeAndStatusAndMinorCallCounter(OPERARTOR, EmployeeStatus.ACTIVE);
        assertFalse(actual.isPresent());
    }

    @Test
    public void testGetByTypeAndStatusAndMinorCallCounterMustFoundOperator() {
        EmployeeDAO employeeDAO = new EmployeeDAOMemoryImpl();
        long callCounterExpected = 3;

        TestUtil.bulkBuilderSupervisor(1, 5).forEach(employeeDAO::create);
        TestUtil.bulkBuilderOperator(callCounterExpected, 7).forEach(employeeDAO::create);

        Optional<Employee> actual = employeeDAO.getByTypeAndStatusAndMinorCallCounter(OPERARTOR,
                EmployeeStatus.ACTIVE);
        assertTrue(actual.isPresent());

        Employee employee = actual.get();
        assertEquals(callCounterExpected, employee.getCallCounter());
        assertEquals(OPERARTOR, employee.getEmployeeType());
        assertEquals(EmployeeStatus.ACTIVE, employee.getEmployeeStatus());
    }
}
