package com.ntapia.callcenter.employee;

import com.ntapia.callcenter.common.EmployeeNotAvailableException;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
public interface EmployeeService {

    Employee save(Employee employee);

    Employee getAvailable() throws EmployeeNotAvailableException;
}
