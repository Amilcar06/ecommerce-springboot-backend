package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.AuthDTO.CreateVendedorRequest;
import com.plataforma.ecommerce.model.entity.User;
import com.plataforma.ecommerce.exception.UserProfileException;

public interface IAdminUserService {
    User createVendedor(CreateVendedorRequest request) throws UserProfileException;
}
