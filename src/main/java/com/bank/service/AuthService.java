package com.bank.service;

import com.bank.dto.request.LoginRequest;
import com.bank.dto.request.RegisterRequest;
import com.bank.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

     AuthResponse login(LoginRequest request);
}
