package com.project.food_delivery.service;

import com.project.food_delivery.entity.UserEntity;

public interface LoginService {
    public abstract boolean checkLogin(String email, String password);

    public abstract UserEntity checkLogin(String email);
}
