package com.ntapia.callcenter.call;

/**
 * @author Neider Tapia <ntapia@boomi.com>.
 */
class OnlineState extends CallState{

    OnlineState(CallProcess process) {
        super(process);
    }

    @Override
    void makeCall() {
        throw new IllegalStateException("No se puede hacer una llamada cuando se esta atendiendo a un cliente!");
    }

    @Override
    void assignEmployee() {
        throw new IllegalStateException("No se puede asignar un empleado cuando se esta atendiendo a un cliente!");
    }

}
