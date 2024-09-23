package com.bianca.trading.service;

import com.bianca.trading.domain.OrderType;
import com.bianca.trading.modal.Coin;
import com.bianca.trading.modal.Order;
import com.bianca.trading.modal.OrderItem;
import com.bianca.trading.modal.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);


    Order getOrderById(long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}
