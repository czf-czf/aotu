package com.aistar.service.impl;

import com.aistar.pojo.Customer;
import com.aistar.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public Customer getByUsernameAndPwd(String name, String password) {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer("qaz","123"));
        customerList.add(new Customer("wsx","123"));
        customerList.add(new Customer("edc","123"));
        customerList.add(new Customer("rfv","123"));
        customerList.add(new Customer("tgb","123"));
        for (Customer customer:customerList){
            if(customer.getUsername().equals(name) && customer.getUserpassword().equals(password)){
                return customer;
            }
        }
        return null;
    }
}
