package com.duoc.SendSignalKafka.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.duoc.SendSignalKafka.client.PatientClient;
import com.duoc.SendSignalKafka.client.VitalSignClient;
import com.duoc.SendSignalKafka.models.Patient;
import com.duoc.SendSignalKafka.models.VitalSign;
import com.duoc.SendSignalKafka.service.KafkaSenderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class SendSignalTask {

    private final KafkaSenderService kafkaSenderService;
    private final PatientClient patientClient;
    private final VitalSignClient vitalSignClient;
    

    public SendSignalTask(KafkaSenderService kafkaSenderService, PatientClient patientClient,
            VitalSignClient vitalSignClient) {
        this.kafkaSenderService = kafkaSenderService;
        this.patientClient = patientClient;
        this.vitalSignClient = vitalSignClient;
    }


    @Scheduled(fixedRate = 1000)
    public void generateVitalSign() {

        ResponseEntity<List<Patient>> response = this.patientClient.getAllPatients();

        for (Patient patient : response.getBody()) {

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

            this.vitalSignClient.createVitalSign(newVitalSign);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            try {
                kafkaSenderService.sendMessage(objectMapper.writeValueAsString(newVitalSign));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }

    }

}
