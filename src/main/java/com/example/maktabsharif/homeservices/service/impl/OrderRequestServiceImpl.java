package com.example.maktabsharif.homeservices.service.impl;

import com.example.maktabsharif.homeservices.repository.OrdersRepository;
import com.example.maktabsharif.homeservices.service.OrderRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderRequestServiceImpl implements OrderRequestService {
    private  final OrdersRepository ordersRepository;
}
