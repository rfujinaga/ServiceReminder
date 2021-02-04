package com.example.ServiceReminder.domain.dao.entity;

import lombok.Data;

@Data
public class Service {
    private String serviceName;
    private String registrationDate;
    private String period;
    private String mailAddress;
    private String cardBrand;
    private String cardNum;
    private String serviceId;
    private String password;
    private String memo;
}
