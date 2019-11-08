package com.nsa.charitystarter.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SponsorForm {


    @NotNull
    @Size(min = 2, max = 30, message = "Invalid title")
    private String fundraiserName;

    @NotNull
    @Size(min = 2, max = 100, message = "Invalid Action")
    private String fundraisingAction;

    @NotNull
    @Size(min = 2, max = 30, message = "Invalid furl")
    private String furl;

}
