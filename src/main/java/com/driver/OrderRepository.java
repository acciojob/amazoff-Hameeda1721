package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@Repository
public class OrderRepository {
    private HashMap<String , Order> orderMap;
    private HashMap<String,DeliveryPartner> deliveryPartnerHashMap;
    private HashMap<String,List<String>> orderToDeliveryPartner;
    private HashMap<String,List<String>> partnerIDMap;

    public OrderRepository(HashMap<String,Order> orderMap,HashMap<String,DeliveryPartner> deliveryPartnerHashMap,HashMap<String,List<String>> orderToDeliveryPartner,HashMap<String,List<String>> partnerIDMap){
        this.orderMap = orderMap;
        this.deliveryPartnerHashMap = deliveryPartnerHashMap;
        this.orderToDeliveryPartner = orderToDeliveryPartner;
        this.partnerIDMap = partnerIDMap;
    }

    public OrderRepository() {

    }

    public HashMap<String, Order> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(HashMap<String, Order> orderMap) {
        this.orderMap = orderMap;
    }

    public HashMap<String, DeliveryPartner> getDeliveryPartnerHashMap() {
        return deliveryPartnerHashMap;
    }

    public void setDeliveryPartnerHashMap(HashMap<String, DeliveryPartner> deliveryPartnerHashMap) {
        this.deliveryPartnerHashMap = deliveryPartnerHashMap;
    }

    public HashMap<String, List<String>> getOrderToDeliveryPartner() {
        return orderToDeliveryPartner;
    }

    public void setOrderToDeliveryPartner(HashMap<String, List<String>> orderToDeliveryPartner) {
        this.orderToDeliveryPartner = orderToDeliveryPartner;
    }

    public HashMap<String, List<String>> getPartnerIDMap() {
        return partnerIDMap;
    }

    public void setPartnerIDMap(HashMap<String, List<String>> partnerIDMap) {
        this.partnerIDMap = partnerIDMap;
    }

    public void addOrder(Order order) {
        orderMap.put(order.getId(),order);
    }


    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner = new DeliveryPartner();
        deliveryPartner.setId(partnerId);
        deliveryPartner.setNumberOfOrders(0);
        deliveryPartnerHashMap.put(partnerId,deliveryPartner);
    }

    public void addOrderToPartner(String orderId, String partnerId) {
        List<String> orderListOfId = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        if(orderMap.containsKey(orderId) && deliveryPartnerHashMap.containsKey(partnerId)){
            if(orderToDeliveryPartner.containsKey(partnerId)){
                orderListOfId = orderToDeliveryPartner.get(partnerId);
            }
            orderListOfId.add(orderId);
            orderToDeliveryPartner.put(partnerId,orderListOfId);
            Order order = orderMap.get(orderId);
            String time = String.valueOf(order.getDeliveryTime());

            if(partnerIDMap.containsKey(partnerId)){
                timeList=partnerIDMap.get(partnerId);
            }
            timeList.add(time);
            partnerIDMap.put(time,timeList);
        }
    }

    public Order getOrderById(String orderId) {
        return orderMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerHashMap.get(partnerId);
    }

    public Integer getOrderCountByPartner(String partnerId) {
        List<String> orderList = new ArrayList<>();
        int c=0;
        if(orderToDeliveryPartner.containsKey(partnerId)){
            orderList = orderToDeliveryPartner.get(partnerId);
            c = orderList.size();
        }
        return c;
    }

    public List<String> getOrdersByPartner(String partnerId) {
        List<String> orderList = new ArrayList<>();
        if(orderToDeliveryPartner.containsKey(partnerId)){
            orderList=orderToDeliveryPartner.get(partnerId);
        }
        return orderList;
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        int totalNoOfOrders = orderMap.size();
        for (String orders : orderMap.keySet()){
            for (String partner : orderToDeliveryPartner.keySet()){
                List<String> orderList = new ArrayList<>();
                orderList=orderToDeliveryPartner.get(partner);
                for (String order : orderList){
                    if(orders.equals(order)){
                        totalNoOfOrders -- ;
                        break;
                    }
                }
            }
        }
        return totalNoOfOrders;
    }

    public Integer getCountOfOrdersLeftAfterGivenTime(String time, String partnerId) {
        Order order=new Order();

        int convertedTime= order.convertTimeToInt(time);
        int count=0;
        if(partnerIDMap.containsKey(partnerId)){
            List<String> timeList=new ArrayList<>();
            timeList= partnerIDMap.get(partnerId);
            for(String times: timeList){
                int newTime= order.convertTimeToInt(times);
                if(newTime>convertedTime){
                    count++;
                }
            }
        }
        return count;
    }

    public String getLastDeliveryTime(String partnerId) {
        int maxTime=0;
        String time="";
        Order order=new Order();
        List<String> list=new ArrayList<>();
        if(partnerIDMap.containsKey(partnerId)){
            list= partnerIDMap.get(partnerId);
            for(String times: list){
                int newTime=order.convertTimeToInt(times);
                if(newTime>maxTime){
                    maxTime=newTime;
                }
            }
        }
        time=Integer.toString(maxTime);
        return time;
    }

    public void deletePartnerById(String partnerId) {
        if(deliveryPartnerHashMap.containsKey(partnerId)){
            deliveryPartnerHashMap.remove(partnerId);
        }
        if(orderToDeliveryPartner.containsKey(partnerId)){
            orderToDeliveryPartner.remove(partnerId);
        }
        if(partnerIDMap.containsKey(partnerId)){
            partnerIDMap.remove(partnerId);
        }
    }



    public void deleteOrderById(String orderId) {
        if(orderMap.containsKey(orderId)){
            orderMap.remove(orderId);
        }
        List<String> orderList=new ArrayList<>();
        for(String partnerId: orderToDeliveryPartner.keySet()){
            orderList=orderToDeliveryPartner.get(partnerId);
            for(String orders: orderList){
                if(orders.equals(orderId)){
                    orderList.remove(orders);
                }
            }
            orderToDeliveryPartner.put(partnerId,orderList);
        }
        Order order=new Order();
        String time= String.valueOf(orderMap.get(orderId));

        List<String> timeList=new ArrayList<>();
        for(String partner: partnerIDMap.keySet()){
            timeList=partnerIDMap.get(partner);
            for(String times: timeList){
                if(times.equals(time)){
                    timeList.remove(times);
                }
            }
            partnerIDMap.put(partner,timeList);
        }
    }
}
