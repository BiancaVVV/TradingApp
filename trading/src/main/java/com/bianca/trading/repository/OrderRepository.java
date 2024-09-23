package com.bianca.trading.repository;

import com.bianca.trading.modal.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByUserId(long userId);
}
