package com.plataforma.ecommerce.service;

import com.plataforma.ecommerce.dto.AuthDTO.ChangePasswordRequest;
import com.plataforma.ecommerce.dto.AuthDTO.UpdateProfileRequest;
import com.plataforma.ecommerce.exception.UserProfileException;
import com.plataforma.ecommerce.model.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService {

    User getUserByUsername(String username) throws UsernameNotFoundException;

    User updateProfile(String username, UpdateProfileRequest updateRequest) throws UserProfileException;

    void changePassword(String username, ChangePasswordRequest changePasswordRequest) throws UserProfileException;
}
