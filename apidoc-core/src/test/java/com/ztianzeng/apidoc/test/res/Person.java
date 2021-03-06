package com.ztianzeng.apidoc.test.res;

import com.ztianzeng.apidoc.models.Address;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class Person {
    private Long id;
    private String firstName;
    private Address address;
    private Map<String, String> properties;
    private Date birthDate;
    private Float floatValue;
    private Double doubleValue;


}
