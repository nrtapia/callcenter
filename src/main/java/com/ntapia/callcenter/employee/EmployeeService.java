package com.ntapia.callcenter.employee;

import com.ntapia.callcenter.common.EmployeeNotAvailableException;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
interface EmployeeService {

    Employee get(Long id);

    Employee save(Employee employee);

    Employee getAvailable() throws EmployeeNotAvailableException;
}
