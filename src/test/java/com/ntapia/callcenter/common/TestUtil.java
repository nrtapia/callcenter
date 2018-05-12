package com.ntapia.callcenter.common;

import com.ntapia.callcenter.employee.Employee;
import com.ntapia.callcenter.employee.EmployeeStatus;
import com.ntapia.callcenter.employee.EmployeeType;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.ntapia.callcenter.employee.EmployeeType.OPERARTOR;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
public final class TestUtil {

    private static final Random RANDOM = new Random();

    public static final String NAME_SPIDER = "Spider";
    public static final String NAME_THANOS = "Thanos";

    public static final Long ID = 1234567L;
    public static final Long ONE = 1L;
    public static final Long FIVE = 5L;

    private TestUtil() {
    }


    public static List<Employee> bulkBuilderOperator(long min, long max) {
        return  bulkBuilderStatusActive(OPERARTOR, min, max);
    }

    public static List<Employee> bulkBuilderSupervisor(long min, long max) {
        return  bulkBuilderStatusActive(EmployeeType.SUPERVISOR, min, max);
    }

    public static List<Employee> bulkBuilderDirector(long min, long max) {
        return  bulkBuilderStatusActive(EmployeeType.DIRECTOR, min, max);
    }

    public static List<Employee> bulkBuilderStatusActive(EmployeeType type, long min, long max) {
        return bulkBuilder(type, EmployeeStatus.ACTIVE, min, max);
    }

    public static List<Employee> bulkBuilder(EmployeeType type, EmployeeStatus status, long min, long max) {
        return LongStream.rangeClosed(min, max)
                .mapToObj(value -> buildEmployee(type, status, value))
                .collect(Collectors.toList());
    }

    public static Employee buildEmployee(EmployeeType type, EmployeeStatus status) {
        return buildEmployee(type, status, ONE);
    }

    public static Employee buildEmployee(EmployeeType type, EmployeeStatus status, long callCounter) {
        Long id = RANDOM.nextLong();
        return Employee.builder()
                .employeeType(type)
                .callCounter(callCounter)
                .employeeStatus(status)
                .id(id)
                .name("Employee " + id)
                .build();
    }


}
