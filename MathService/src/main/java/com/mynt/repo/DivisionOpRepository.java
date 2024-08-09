package com.mynt.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mynt.entity.DivisionOperation;

public interface DivisionOpRepository extends JpaRepository<DivisionOperation, Serializable>{

}
