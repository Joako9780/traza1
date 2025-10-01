package org.example.entidades;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(exclude = "localidad")
public class Domicilio {
    private String calle;
    private Integer number;
    private Integer cp;

    private Sucursal sucursal;

    private Localidad localidad;
}
