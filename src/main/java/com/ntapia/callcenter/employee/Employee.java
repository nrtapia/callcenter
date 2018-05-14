package com.ntapia.callcenter.employee;

import lombok.Builder;
import lombok.Data;
import com.ntapia.callcenter.common.Person;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
@Data
public class Employee extends Person {

    private EmployeeType employeeType;
    private EmployeeStatus employeeStatus = EmployeeStatus.INACTIVE;
    private long callCounter;

    @Builder
    public Employee(Long id, String name, EmployeeType employeeType, EmployeeStatus employeeStatus,
            long callCounter) {
        super(id, name);
        this.employeeType = employeeType;
        this.employeeStatus = employeeStatus;
        this.callCounter = callCounter;
    }

    @Override
    public String toString() {
        return employeeType + "_" + getId();
    }
}
