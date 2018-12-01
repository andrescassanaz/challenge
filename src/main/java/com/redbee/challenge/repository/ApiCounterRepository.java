package com.redbee.challenge.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redbee.challenge.model.ApiCounter;

@Repository
public interface ApiCounterRepository extends JpaRepository<ApiCounter, Serializable> {

}
