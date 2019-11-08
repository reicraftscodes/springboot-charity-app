package com.nsa.charitystarter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Charity {

    private Long id;
    private String name;
    private String registrationNumber;
    private String acronym;
    private String tags;

    public String searchable() {
        return String.join(";", name, registrationNumber, acronym, tags);
    }



}
