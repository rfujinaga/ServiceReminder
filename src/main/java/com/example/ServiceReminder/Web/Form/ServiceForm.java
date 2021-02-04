package com.example.ServiceReminder.Web.Form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ServiceForm {
    @NotBlank
    private String serviceName;
    @NotBlank
    private String registrationDate;
    @NotBlank
    private String period;
    @Email
    private String mailAddress;
    private String cardBrand;
    @Size(min = 16,max = 16)
    private String cardNum;
    private String serviceId;
    private String password;
    private String memo;
}
