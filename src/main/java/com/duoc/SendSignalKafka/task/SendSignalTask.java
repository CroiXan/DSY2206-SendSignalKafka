package com.duoc.SendSignalKafka.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.duoc.SendSignalKafka.models.Patient;
import com.duoc.SendSignalKafka.models.VitalSign;
import com.duoc.SendSignalKafka.service.KafkaSenderService;
import com.duoc.SendSignalKafka.service.PatientService;
import com.duoc.SendSignalKafka.service.VitalSignService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class SendSignalTask {

    @Autowired
    private PatientService patientService;

    @Autowired
    private VitalSignService vitalSignService;

    @Autowired
    private KafkaSenderService kafkaSenderService;

    @Scheduled(fixedRate = 1000)
    public void generateVitalSign() {

        List<Patient> patiensList = this.patientService.getAllPatient();

        for (Patient patient : patiensList) {

            int frecuenciaCardiaca = (int) (Math.random() * 301);
            int frecuenciaRespiratoria = (int) (Math.random() * 101);
            int presionArterialSistolica = (int) (Math.random() * 301);
            int presionArterialDiastolica = (int) (Math.random() * 201);
            Double temperaturaCorporal = Math.random() * 50;
            Double saturacionOxigeno = Math.random() * 100;

            VitalSign newVitalSign = new VitalSign();
            newVitalSign.setIdPaciente(patient.getId());
            newVitalSign.setFrecuenciaCardiaca(frecuenciaCardiaca);
            newVitalSign.setFrecuenciaRespiratoria(frecuenciaRespiratoria);
            newVitalSign.setPresionArterialDiastolica(presionArterialDiastolica);
            newVitalSign.setPresionArterialSistolica(presionArterialSistolica);
            newVitalSign.setTemperaturaCorporal(temperaturaCorporal);
            newVitalSign.setSaturacionOxigeno(saturacionOxigeno);
            newVitalSign.setInstante(LocalDateTime.now());

            this.vitalSignService.saveVitalSign(newVitalSign);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            try {
                kafkaSenderService.sendMessage(objectMapper.writeValueAsString(newVitalSign));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }

    }

}
