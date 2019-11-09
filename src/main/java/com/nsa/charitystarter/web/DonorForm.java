package com.nsa.charitystarter.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorForm {
    @NotNull
    @Size(min = 2, max = 30, message = "Invalid name")
    private String name;

    @NotNull
    @Size(min = 2, max = 30, message = "Invalid Address Line")
    private String addressLine1;

    @NotNull
    @Size(min = 2, max = 50, message = "Invalid Address Line")
    private String addressLine2;

    @NotNull
    @Size(min = 2, max = 30, message = "Invalid City")
    private String city;

    // https://www.safaribooksonline.com/library/view/regular-expressions-cookbook/9781449327453/ch04s16.html
    //https://www.regextester.com/97264
    @NotNull
//    @Pattern(regexp = "^([A-PR-UWYZ0-9][A-HK-Y0-9][AEHMNPRTVXY0-9]?[ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLN-UW-Z]{2}|GIR 0AA)$", message = "Invalid postcode")
    private String postcode;

    @NotNull
    private String countryISO;

    private String countryName;

    @NotNull
    @Min(1)
    @NumberFormat(style = NumberFormat.Style.DEFAULT)
    private Double donationAmount;

    @NotNull
    private Boolean isGiftAidEligible = Boolean.FALSE;

}
