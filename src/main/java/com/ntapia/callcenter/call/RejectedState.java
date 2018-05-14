package com.ntapia.callcenter.call;

import java.text.MessageFormat;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
public class RejectedState extends CallState {

    private static final String ERROR_MESSAGE = "Funcion {0} no permitida en estado REJECTED!";
    private static final String FUNCTION_MAKE_CALL = "makeCall";
    private static final String FUNCTION_ASSIGN_EMPLOYEE = "assignEmployee";

    RejectedState(CallProcess process) {
        super(process);
    }

    @Override
    void makeCall() {
        throw new IllegalStateException(MessageFormat.format(ERROR_MESSAGE, FUNCTION_MAKE_CALL));
    }

    @Override
    void assignEmployee() {
        throw new IllegalStateException(MessageFormat.format(ERROR_MESSAGE, FUNCTION_ASSIGN_EMPLOYEE));
    }
}
