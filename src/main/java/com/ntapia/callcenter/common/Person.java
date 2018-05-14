package com.ntapia.callcenter.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Neider Tapia <ntapia@boomi.com>.
 */
@Data
@AllArgsConstructor
@ToString(exclude="name")
public class Person implements Serializable {

    private static final long serialVersionUID = 20180511L;

    private Long id;
    private String name;
}
