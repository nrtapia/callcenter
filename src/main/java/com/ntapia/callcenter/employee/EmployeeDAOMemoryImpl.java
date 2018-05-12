package com.ntapia.callcenter.employee;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
class EmployeeDAOMemoryImpl implements EmployeeDAO{

    private final Map<Long, Employee> memory;
    private final static AtomicLong idCounter = new AtomicLong(1);

    EmployeeDAOMemoryImpl() {
        memory = new ConcurrentHashMap<>();
    }

    @Override
    public Employee create(Employee employee) {
        Long id = idCounter.getAndIncrement();
        employee.setId(id);
        memory.put(id, employee);
        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        memory.put(employee.getId(), employee);
        return memory.get(employee.getId());
    }

    @Override
    public Optional<Employee> getByTypeAndStatusAndMinorCallCounter(EmployeeType type, EmployeeStatus status) {
        return memory.values().stream()
                .filter(empl ->  type == empl.getEmployeeType() && status == empl.getEmployeeStatus() )
                .min(Comparator.comparingLong(Employee::getCallCounter));
    }
}
