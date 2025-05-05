package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
    @Query("select o from Orders  o where o.specialistAccepted is null")
    List<Orders> getListOrderSpecialistNull();

    List<Orders>getOrdersByCustomerRequestServiceId(Long customerRequestedId);

}
