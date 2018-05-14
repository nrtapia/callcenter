package com.ntapia.callcenter.dispatcher;

import com.ntapia.callcenter.call.CallProcess;
import com.ntapia.callcenter.common.Client;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
public class DispatcherSemaphoreImpl implements Dispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherSemaphoreImpl.class);

    private final Semaphore semaphore;
    private final ExecutorService executor;

    public DispatcherSemaphoreImpl(int limit) {
        semaphore = new Semaphore(limit, true);

        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("DispatcherThread-%d")
                .build();
        executor = Executors.newFixedThreadPool(limit, factory);
    }

    @Override
    public void dispatchCall(Client client) {
        LOGGER.info("Disponibilidad del semaforo {}, para {}", semaphore.availablePermits(), client);

        boolean acquired = semaphore.tryAcquire();
        if (acquired) {
            executor.submit(new CallProcess(semaphore, client));
            LOGGER.debug("Llamada despachada {}", client);
        }else{
            LOGGER.warn("XXX  Empleados agotados, rechazando llamada  XXX");
        }
    }
}
