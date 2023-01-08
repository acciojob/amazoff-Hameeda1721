package com.driver;

import java.util.List;

public class Orderservice {
    OrderRepository  orderRepository = new OrderRepository();
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }


    public void addOrderToPartner(String orderId, String partnerId) {
        orderRepository.addOrderToPartner(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartner(String partnerId) {
        return orderRepository.getOrderCountByPartner(partnerId);
    }

    public List<String> getOrdersByPartner(String partnerId) {
        return orderRepository.getOrdersByPartner(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getCountOfOrdersLeftAfterGivenTime(String time, String partnerId) {
        return orderRepository.getCountOfOrdersLeftAfterGivenTime(time,partnerId);
    }

    public String getLastDeliveryTime(String partnerId) {
        return orderRepository.getLastDeliveryTime(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
