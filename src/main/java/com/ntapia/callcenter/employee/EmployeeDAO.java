package com.ntapia.callcenter.employee;

import java.util.Optional;

/**
 * @author Neider Tapia <ntapia@boomi.com>.
 */
interface EmployeeDAO {

    Employee create(Employee employee);

    Employee update(Employee employee);

    Optional<Employee> getByTypeAndStatusAndMinorCallCounter(EmployeeType type, EmployeeStatus status);
}
