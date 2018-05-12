package com.ntapia.callcenter.model;

import lombok.Data;

import com.ntapia.callcenter.employee.Employee;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Neider Tapia <ntapia@boomi.com>.
 */
@Data
public class Call implements Serializable{

    private static final long serialVersionUID = 20180511L;

    private Employee _employee;
    private final Client _client;
    private CallStatus _callStatus;
    private final LocalDateTime _start;
    private LocalDateTime _end;

    public Call(Client client) {
        _client = client;
        _callStatus = CallStatus.INCOMING;
        _start = LocalDateTime.now();
    }
}
