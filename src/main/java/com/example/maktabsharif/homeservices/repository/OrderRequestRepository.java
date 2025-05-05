package com.example.maktabsharif.homeservices.repository;

import com.example.maktabsharif.homeservices.dto.order.OrderRequestDTO;
import com.example.maktabsharif.homeservices.entity.OrderRequest;
import com.example.maktabsharif.homeservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRequestRepository extends JpaRepository<OrderRequest, Long> {
    @Query("SELECT o FROM OrderRequest o WHERE o.order.id = :orderId AND o.SpecialistAcceptRequest.id = :specialistId")
    Optional<OrderRequest> findOrderRequestByOrderIdAndSpecialistAcceptRequestId
            (@Param("orderId") Long orderId, @Param("specialistId") Long specialistId);

    @Query("select c.SpecialistAcceptRequest from  OrderRequest c where c.order.customerRequestService.id = :customerOrderRequest")
    List<User> findAllSpecialistAcceptRequest(@Param("customerOrderRequest") Long customerOrderRequest);

    @Query("select order from OrderRequest order where order.order.id = :orderRequestId")
    List<OrderRequest> getListRequestedSpecialist(@Param("orderRequestId") Long orderRequestId);
   // @Query
    //List<OrderRequest> findAllByOrder_IdAndSpecialistAcceptRequest_Id(Long orderId, Long specialistAcceptRequestId);


}
