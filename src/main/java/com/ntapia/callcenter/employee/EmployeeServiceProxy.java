// Copyright (c) 2018 Boomi, Inc.

package com.ntapia.callcenter.employee;

import com.ntapia.callcenter.common.EmployeeNotAvailableException;

/**
 * @author Neider Tapia <ntapia@boomi.com>.
 */
public final class EmployeeServiceProxy implements EmployeeService {

    private EmployeeServiceProxy() {
    }

    private static class SingletonHelper {
        private static final EmployeeService instance = new EmployeeServiceImpl(new EmployeeDAOMemoryImpl());
    }

    private static EmployeeService getInstance() {
        return SingletonHelper.instance;
    }

    @Override
    public Employee save(Employee employee) {
        return getInstance().save(employee);
    }

    @Override
    public Employee getAvailable() throws EmployeeNotAvailableException {
        return getInstance().getAvailable();
    }
}
