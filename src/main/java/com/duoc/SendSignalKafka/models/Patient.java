package com.duoc.SendSignalKafka.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "patientdsy2205") 
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @NotNull(message = "La El Rut no puede ser nulo.")
    @Size(min = 2, max = 10, message = "El Rut debe tener entre 3 y 10 caracteres.")
    @Column(name = "RUT", length = 10)
    private String rut;

    @NotNull(message = "La El password no puede ser nulo.")
    @Size(min = 1, max = 128, message = "El password debe tener entre 1 y 128 caracteres.")
    @Column(name = "PASSWORD", length = 128)
    private String password;

    @NotNull(message = "El Nombre no puede ser nulo.")
    @Size(min = 1, max = 256, message = "El nombre debe tener entre 1 y 256 caracteres.")
    @Column(name = "NOMBRE", length = 256)
    private String nombre;

    @NotNull(message = "Apellidos no puede ser nulo.")
    @Size(min = 1, max = 256, message = "El nombre debe tener entre 1 y 256 caracteres.")
    @Column(name = "APELLIDOS", length = 256)
    private String apellidos;

    @NotNull(message = "Email es requerido y no puede ser nulo.")
    @Email(message = "Debe ser un Email valido.")
    private String email;

    @Past(message = "La fecha debe ser menor o igual a la actual en este formato = dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate bornDate; 
}
