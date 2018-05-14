package com.ntapia.callcenter.employee;

import com.ntapia.callcenter.common.EmployeeNotAvailableException;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public Employee get(Long id) {
        return employeeDAO.getById(id);
    }

    @Override
    public Employee save(Employee employee) {
        if (Objects.isNull(employee)) {
            throw new IllegalArgumentException("The employee it's required!");
        }

        return Objects.nonNull(employee.getId())
                ? employeeDAO.update(employee)
                : employeeDAO.create(employee);
    }

    @Override
    public Employee getAvailable() throws EmployeeNotAvailableException {
        return findAvailableByType(EmployeeType.OPERATOR)
                .orElse(findAvailableByType(EmployeeType.SUPERVISOR)
                .orElse(findAvailableByType(EmployeeType.DIRECTOR)
                        .orElseThrow(EmployeeNotAvailableException::new)));
    }

    private Optional<Employee> findAvailableByType(EmployeeType type) {
        return employeeDAO.getByTypeAndStatusAndMinorCallCounter(type, EmployeeStatus.ACTIVE);
    }
}
