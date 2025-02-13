package com.duoc.SendSignalKafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duoc.SendSignalKafka.models.VitalSign;

public interface VitalSignRepository extends JpaRepository<VitalSign,Integer>{
    
}
