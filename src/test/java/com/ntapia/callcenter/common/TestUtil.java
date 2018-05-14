package com.ntapia.callcenter.common;

import com.ntapia.callcenter.employee.Employee;
import com.ntapia.callcenter.employee.EmployeeStatus;
import com.ntapia.callcenter.employee.EmployeeType;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.ntapia.callcenter.employee.EmployeeType.OPERATOR;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
public final class TestUtil {

    private static AtomicLong employeeCounter = new AtomicLong(0);
    private static AtomicLong clientCounter = new AtomicLong(0);

    public static final String NAME_SPIDER = "Spider";
    public static final String NAME_THANOS = "Thanos";

    public static final Long ID = 1234567L;
    public static final Long ONE = 1L;
    public static final Long FIVE = 5L;

    private TestUtil() {
    }


    public static List<Employee> bulkBuilderOperator(long min, long max) {
        return  bulkBuilderStatusActive(OPERATOR, min, max);
    }

    public static List<Employee> bulkBuilderSupervisor(long min, long max) {
        return  bulkBuilderStatusActive(EmployeeType.SUPERVISOR, min, max);
    }

    public static List<Employee> bulkBuilderDirector(long min, long max) {
        return  bulkBuilderStatusActive(EmployeeType.DIRECTOR, min, max);
    }

    public static List<Employee> bulkBuilderStatusActive(EmployeeType type, long min, long max) {
        return bulkClientBuilder(type, EmployeeStatus.ACTIVE, min, max);
    }

    public static List<Employee> bulkClientBuilder(EmployeeType type, EmployeeStatus status, long min, long max) {
        return LongStream.rangeClosed(min, max)
                .mapToObj(value -> buildEmployee(type, status, value))
                .collect(Collectors.toList());
    }

    public static Employee buildEmployee(EmployeeType type, EmployeeStatus status) {
        return buildEmployee(type, status, ONE);
    }

    private static Employee buildEmployee(EmployeeType type, EmployeeStatus status, long callCounter) {
        Long id = employeeCounter.incrementAndGet();
        return Employee.builder()
                .employeeType(type)
                .callCounter(callCounter)
                .employeeStatus(status)
                .id(id)
                .name(type + "_" + id)
                .build();
    }

    public static Client buildClient() {
        Long id = clientCounter.incrementAndGet();
        return Client.builder()
                .id(id)
                .name("Client_" + id)
                .build();
    }

    public static List<Client> bulkClientBuilder(int cant) {
        return LongStream.rangeClosed(1, cant)
                .mapToObj(value -> buildClient())
                .collect(Collectors.toList());
    }
}
