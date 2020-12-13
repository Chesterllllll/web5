package com.example.web5.dao;

import com.example.web5.Entity.AddressList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressListRepository extends CrudRepository<AddressList, Long> {
    List<AddressList> findByName(String name);
    List<AddressList> findByPhone(String phone);
    List<AddressList> findByEmail(String email);
    List<AddressList> findByAddress(String address);
    List<AddressList> findByQq(String qq);
}