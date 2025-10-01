package org.example.entidades;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(exclude = "provincia")
public class Localidad {
    private String nombre;
    private Provincia provincia;

    @Builder.Default
    private Set<Domicilio> domicilios = new HashSet<>();
}
