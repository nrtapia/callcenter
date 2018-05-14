// Copyright (c) 2018 Boomi, Inc.

package com.ntapia.callcenter.call;

import com.ntapia.callcenter.common.EmployeeNotAvailableException;
import com.ntapia.callcenter.employee.Employee;
import com.ntapia.callcenter.employee.EmployeeServiceProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Neider Tapia <ntapia@boomi.com>.
 */
class IncomingState extends CallState {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingState.class);

    IncomingState(CallProcess process) {
        super(process);
    }

    @Override
    void makeCall() {
    }

    @Override
    void assignEmployee() throws EmployeeNotAvailableException {
        LOGGER.debug("Buscando un empleado disponible para atender la llamada");

        Employee employee = EmployeeServiceProxy.getAvailableAndChangeToBusy();
        LOGGER.info("La llamada sera atendida por {} counter {}", employee.getName(), employee.getCallCounter());

        CallState next = callProcess.getOnlineState();
        next.setEmployee(employee);
        callProcess.setState(next);
    }
}
