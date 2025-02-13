package com.duoc.SendSignalKafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.SendSignalKafka.models.VitalSign;
import com.duoc.SendSignalKafka.repository.VitalSignRepository;

@Service
public class VitalSignService {

    @Autowired
    private VitalSignRepository vitalSignRepository;

    public VitalSign saveVitalSign (VitalSign vitalSign){
        return vitalSignRepository.save(vitalSign);
    }

}
