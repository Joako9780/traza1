package org.example.entidades;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "provincia")
public class Localidad {
    private Long id;
    private String nombre;
    private Provincia provincia;

    @Builder.Default
    private Set<Domicilio> domicilios = new HashSet<>();
}
