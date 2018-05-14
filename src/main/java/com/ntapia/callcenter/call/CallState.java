package com.ntapia.callcenter.call;

import lombok.Getter;
import lombok.Setter;
import com.ntapia.callcenter.common.EmployeeNotAvailableException;
import com.ntapia.callcenter.employee.Employee;
import com.ntapia.callcenter.employee.EmployeeServiceProxy;

import java.time.LocalDateTime;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
@Getter
@Setter
abstract class CallState {

    private Employee employee;
    private final LocalDateTime start;
    private LocalDateTime end;
    protected final CallProcess callProcess;

    CallState(CallProcess process) {
        callProcess = process;
        start = LocalDateTime.now();
    }

    void endCall(){
        EmployeeServiceProxy.changeStatusToActive(getEmployee().getId());

        end = LocalDateTime.now();
        callProcess.setState(callProcess.getOfflineState());
    }

    abstract void makeCall();

    abstract void assignEmployee() throws EmployeeNotAvailableException;

    void rejectCall(){
        setEnd(LocalDateTime.now());
        callProcess.setState(callProcess.getRejectedCall());
    }

}
