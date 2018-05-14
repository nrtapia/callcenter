package com.ntapia.callcenter.call;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
class OfflineState extends CallState{

    OfflineState(CallProcess process) {
        super(process);
    }

    @Override
    void assignEmployee() {
        throw new IllegalStateException("No se puede asignar un empleado cuando no hay una llamada!");
    }

    @Override
    void makeCall() {
        callProcess.setState(callProcess.getIncomingState());
    }
}
