package com.ntapia.callcenter.model;

import lombok.Builder;
import lombok.Data;
import com.ntapia.callcenter.common.Person;

/**
 * @author Neider Tapia <ntapia@boomi.com>.
 */
@Data
public class Client extends Person {

    @Builder
    public Client(Long id, String name) {
        super(id, name);
    }
}
