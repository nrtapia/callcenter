package com.ntapia.callcenter.employee;

import com.ntapia.callcenter.common.EmployeeNotAvailableException;
import com.ntapia.callcenter.common.TestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.ntapia.callcenter.common.TestUtil.FIVE;
import static com.ntapia.callcenter.common.TestUtil.ID;
import static com.ntapia.callcenter.common.TestUtil.NAME_SPIDER;
import static com.ntapia.callcenter.common.TestUtil.ONE;
import static com.ntapia.callcenter.employee.EmployeeStatus.ACTIVE;
import static com.ntapia.callcenter.employee.EmployeeStatus.BUSY;
import static com.ntapia.callcenter.employee.EmployeeStatus.INACTIVE;
import static com.ntapia.callcenter.employee.EmployeeType.DIRECTOR;
import static com.ntapia.callcenter.employee.EmployeeType.OPERATOR;
import static com.ntapia.callcenter.employee.EmployeeType.SUPERVISOR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
@RunWith(value = MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

    public static final int TEN = 10;
    @Mock
    private EmployeeDAO mockEmployeeDAO;

    @Test(expected = IllegalArgumentException.class)
    public void testSaveMustValidateNotNullArgument() {
        EmployeeService service = new EmployeeServiceImpl(mockEmployeeDAO);
        service.save(null);
    }

    @Test
    public void testSaveMustBeCallCreateInDAO() {
        Employee expected = Employee.builder().name(NAME_SPIDER).build();
        when(mockEmployeeDAO.create(expected)).thenReturn(Employee.builder().name(NAME_SPIDER).id(ID).build());
        EmployeeService service = new EmployeeServiceImpl(mockEmployeeDAO);

        Employee employee = service.save(expected);
        assertNotNull(employee);
        verify(mockEmployeeDAO, times(ONE.intValue())).create(expected);
        verify(mockEmployeeDAO, never()).update(any(Employee.class));
    }

    @Test
    public void testSaveMustBeCallUpdateInDAO() {
        Employee expected = Employee.builder().name(NAME_SPIDER).id(ID).build();
        when(mockEmployeeDAO.update(expected)).thenReturn(Employee.builder().name(NAME_SPIDER).id(ID).build());
        EmployeeService service = new EmployeeServiceImpl(mockEmployeeDAO);

        Employee employee = service.save(expected);
        assertNotNull(employee);
        verify(mockEmployeeDAO, times(ONE.intValue())).update(expected);
        verify(mockEmployeeDAO, never()).create(any(Employee.class));
    }

    @Test(expected = EmployeeNotAvailableException.class)
    public void testAvailableNotFoundMustThrowEmployeeNotAvailableException() throws EmployeeNotAvailableException {

        EmployeeDAO employeeDAO = new EmployeeDAOMemoryImpl();
        TestUtil.bulkClientBuilder(OPERATOR,     BUSY,       ONE, FIVE).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(OPERATOR,     INACTIVE,   ONE, FIVE).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(SUPERVISOR,    BUSY,       ONE, FIVE).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(DIRECTOR,      INACTIVE,   ONE, FIVE).forEach(employeeDAO::create);

        EmployeeService service = new EmployeeServiceImpl(employeeDAO);
        service.getAvailable();
    }

    @Test
    public void testAvailableFoundOperatorMustReturnMinCallCounter() throws EmployeeNotAvailableException {

        long minCounter = 3;
        EmployeeDAO employeeDAO = new EmployeeDAOMemoryImpl();
        TestUtil.bulkClientBuilder(SUPERVISOR,    ACTIVE,   ONE, FIVE).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(SUPERVISOR,    INACTIVE, ONE, FIVE).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(DIRECTOR,      ACTIVE,   ONE, FIVE).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(OPERATOR,     BUSY,     FIVE, TEN).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(OPERATOR,     ACTIVE,   minCounter, FIVE).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(OPERATOR,     INACTIVE, ONE, FIVE).forEach(employeeDAO::create);

        EmployeeService service = new EmployeeServiceImpl(employeeDAO);
        Employee employee = service.getAvailable();

        assertNotNull(employee);
        assertEquals(OPERATOR, employee.getEmployeeType());
        assertSame(minCounter, employee.getCallCounter());
        assertEquals(ACTIVE, employee.getEmployeeStatus());
    }

    @Test
    public void testAvailableNotFoundOperatorMustReturnMinCallCounter() throws EmployeeNotAvailableException {

        long minCounter = 3;
        EmployeeDAO employeeDAO = new EmployeeDAOMemoryImpl();
        TestUtil.bulkClientBuilder(SUPERVISOR,    ACTIVE,   minCounter, FIVE).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(SUPERVISOR,    INACTIVE, ONE, FIVE).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(DIRECTOR,      ACTIVE,   ONE, FIVE).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(OPERATOR,     BUSY,     FIVE, TEN).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(OPERATOR,     INACTIVE, ONE, FIVE).forEach(employeeDAO::create);

        EmployeeService service = new EmployeeServiceImpl(employeeDAO);
        Employee employee = service.getAvailable();

        assertNotNull(employee);
        assertEquals(SUPERVISOR, employee.getEmployeeType());
        assertSame(minCounter, employee.getCallCounter());
        assertEquals(ACTIVE, employee.getEmployeeStatus());
    }

    @Test
    public void testAvailableNotFoundSupervisorMustReturnMinCallCounter() throws EmployeeNotAvailableException {

        long minCounter = FIVE;
        EmployeeDAO employeeDAO = new EmployeeDAOMemoryImpl();
        TestUtil.bulkClientBuilder(SUPERVISOR,    INACTIVE, ONE, TEN).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(DIRECTOR,      INACTIVE, ONE, FIVE).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(DIRECTOR,      ACTIVE,   minCounter, TEN).forEach(employeeDAO::create);
        TestUtil.bulkClientBuilder(OPERATOR,     BUSY,     ONE, TEN).forEach(employeeDAO::create);

        EmployeeService service = new EmployeeServiceImpl(employeeDAO);
        Employee employee = service.getAvailable();

        assertNotNull(employee);
        assertEquals(DIRECTOR, employee.getEmployeeType());
        assertSame(minCounter, employee.getCallCounter());
        assertEquals(ACTIVE, employee.getEmployeeStatus());
    }
}