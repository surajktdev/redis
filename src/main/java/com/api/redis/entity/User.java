package com.api.redis.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String userId;
    private String name;
    private String email;
    private String phone;
}
