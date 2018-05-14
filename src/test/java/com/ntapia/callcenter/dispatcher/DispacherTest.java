package com.ntapia.callcenter.dispatcher;

import com.ntapia.callcenter.common.TestUtil;
import com.ntapia.callcenter.employee.EmployeeServiceProxy;
import com.ntapia.callcenter.employee.EmployeeType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.LongStream;

/**
 * @author Neider Tapia <neider.tapia@gmail.com>.
 */
@RunWith(Parameterized.class)
public class DispacherTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispacherTest.class);

    private static final int MIN = 1;

    private int dispatcherLimit;
    private int activeOperators;
    private int activeSupervisor;
    private int activeDirectors;
    private int concurrentCalls;


    public DispacherTest(int dispatcherLimit, int activeOperators, int activeSupervisor, int activeDirectors,
            int concurrentCalls) {
        this.dispatcherLimit = dispatcherLimit;
        this.activeOperators = activeOperators;
        this.activeSupervisor = activeSupervisor;
        this.activeDirectors = activeDirectors;
        this.concurrentCalls = concurrentCalls;
    }

    @Parameterized.Parameters(name = "dispacher:{0}_operadores:{1}_supervisores:{2}_directores:{3}_llamadas:{4}")
    public static Collection<Object[]>  primeNumbers() {
        return Arrays.asList(new Object[][]{
                { 10, 10, 3, 2, 12 },
                { 10, 7, 2, 2, 10 }
        });
    }

    @Before
    public void setup(){

        TestUtil.bulkBuilderStatusActive(EmployeeType.OPERATOR, MIN, activeOperators)
                .forEach(EmployeeServiceProxy::save);

        TestUtil.bulkBuilderStatusActive(EmployeeType.SUPERVISOR, MIN, activeSupervisor)
                .forEach(EmployeeServiceProxy::save);

        TestUtil.bulkBuilderStatusActive(EmployeeType.DIRECTOR, MIN, activeDirectors)
                .forEach(EmployeeServiceProxy::save);
    }


    @Test
    public void testDispatcher() throws InterruptedException {

        final Dispatcher dispatcher = new DispatcherSemaphoreImpl(dispatcherLimit);

        Runnable task = () -> {
            dispatcher.dispatchCall(TestUtil.buildClient());
        };

        LongStream.range(1, concurrentCalls).forEach((value) -> {
            Thread thread = new Thread(task);
            thread.start();
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                LOGGER.error("Thread interrupted", e);
            }
        });


        Thread.sleep(15000);
    }

}
