package com.ntapia.callcenter.call;

import lombok.Getter;
import com.ntapia.callcenter.common.EmployeeNotAvailableException;
import com.ntapia.callcenter.common.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
@Getter
public class CallProcess implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallProcess.class);

    private final Semaphore semaphore;
    private CallState state;
    private final Client client;

    private CallState offlineState;
    private CallState rejectedCall;
    private CallState incomingState;
    private CallState onlineState;

    void setState(CallState state) {
        this.state = state;
    }

    public CallProcess(Semaphore semaphore, Client client) {
        this.semaphore = semaphore;
        this.client = client;

        offlineState = new OfflineState(this);
        rejectedCall = new RejectedState(this);
        incomingState = new IncomingState(this);
        onlineState = new OnlineState(this);
        state = offlineState;
    }

    @Override
    public void run() {

        LOGGER.info("Atendiendo llamada {}...", client);
        state.makeCall();

        try {
            LOGGER.debug("Buscar empleado para asignar {}...", client);
            state.assignEmployee();

            LOGGER.debug("Iniciar conversacion {}...", client);
            startConversation();

            LOGGER.info("Finalizando llamada entre {} y {}", client, state.getEmployee());
            state.endCall();
        }
        catch (EmployeeNotAvailableException e) {
            LOGGER.warn("No hay empleado disponible {}, rechazando llamada...", client);
            state.rejectCall();
        }
        finally {
            semaphore.release();
        }
    }


    private void startConversation() {
        int callDuration = ThreadLocalRandom.current().nextInt(5, 10);
        LOGGER.info(">>> Inicia conversacion cliente {} y empleado {}, duracion: {}", client, state.getEmployee(),
                callDuration);

        try {
            // TODO Simulacion del proceso de llamada
            TimeUnit.SECONDS.sleep(callDuration);
        }
        catch (InterruptedException e) {
            LOGGER.error("La conversacion fue interunpida!", e);
            state.endCall();
        }
    }
}
