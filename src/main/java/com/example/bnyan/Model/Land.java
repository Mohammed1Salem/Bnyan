package com.example.bnyan.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Land {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "location must be filled")
    @Column(columnDefinition = "varchar(100) not null")
    private String location;

    @NotNull(message = "size must be filled")
    @Column(columnDefinition = "varchar(30) not null")
    private String size;

    @Column(columnDefinition = "Boolean not null")
    private Boolean authorizationStatus;

    @Column(columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @OneToOne(mappedBy = "land")
    @JsonIgnore
    private Project project;
}