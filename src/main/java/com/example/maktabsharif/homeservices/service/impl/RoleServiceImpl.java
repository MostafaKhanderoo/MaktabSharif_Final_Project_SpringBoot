package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.entity.Role;
import com.example.maktabsharif.homeservices.exception.CustomApiExceptionType;
import com.example.maktabsharif.homeservices.exception.NotFoundException;
import com.example.maktabsharif.homeservices.repository.RoleRepository;
import com.example.maktabsharif.homeservices.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(()-> new NotFoundException("Role "+name+" not found!"
                , CustomApiExceptionType.NOT_FOUND));

    }
}
