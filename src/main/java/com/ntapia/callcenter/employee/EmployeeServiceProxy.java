// Copyright (c) 2018 Boomi, Inc.

package com.ntapia.callcenter.employee;

import com.ntapia.callcenter.common.EmployeeNotAvailableException;

import java.util.Objects;

/**
 * @author Neider Tapia <ntapia@boomi.com>.
 */
public final class EmployeeServiceProxy{

    private EmployeeServiceProxy() {
    }

    private static class SingletonHelper {
        private static final EmployeeService instance = new EmployeeServiceImpl(new EmployeeDAOMemoryImpl());
    }

    private static EmployeeService getInstance() {
        return SingletonHelper.instance;
    }

    public static Employee save(Employee employee) {
        return getInstance().save(employee);
    }

    public static synchronized Employee getAvailableAndChangeToBusy() throws EmployeeNotAvailableException {
        Employee employee = getInstance().getAvailable();
        employee.setCallCounter(employee.getCallCounter() + 1);
        employee.setEmployeeStatus(EmployeeStatus.BUSY);
        return getInstance().save(employee);
    }

    public static void changeStatusToActive(Long id){
        Employee employee = getInstance().get(id);
        if(Objects.nonNull(employee)){
            employee.setEmployeeStatus(EmployeeStatus.ACTIVE);
            getInstance().save(employee);
        }
    }
}
