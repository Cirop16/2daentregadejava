package com.coderhouse.biller.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Schema(description = "entity representing the client" ,example = "{\"name\":\"Ciro\",\"lastname\":\"Pupillo\",\"mail\":\"ciropupillo@gmail.com\",\"cellphone\":\"2213456789\"}")
@Table(name = "CLIENT")

public class ClientEntity {
    public ClientEntity (UUID id, String name, String lastname, String mail, String cellphone) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.cellphone = cellphone;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public ClientEntity(String name, String lastname, String mail, String cellphone) {
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.cellphone = cellphone;
    }

    @Schema(description = "Client id" , example = "1234567890")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Schema(description = "Client name" , example = "Ciro")
    @Column(nullable = false)
    private String name;

    @Schema(description = "Client lastname" , example = "Pupillo")
    @Column(nullable = false)
    private String lastname;

    @Schema(description = "Client email" ,example = "ciropupillo@gmail.com")
    @Column(nullable = false, unique = true)
    private String mail;

    @Schema(description = "Client cellphone" ,example = "2213456789")
    @Column
    private String cellphone;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.mail = email;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCellphone() {
        return cellphone;
    }
}