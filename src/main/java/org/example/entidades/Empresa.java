package org.example.entidades;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString (exclude = "sucursales")
@EqualsAndHashCode(of = "id")
public class Empresa {
    private Long id;
    private String nombre;
    private String razonSocial;
    private Long cuit;
    private String logo;

    @Builder.Default
    private Set<Sucursal> sucursales = new HashSet<>();  //Set no permite elementos repetidos

    public void agregarSucursal(Sucursal sucursal){
        sucursales.add(sucursal);
    }
}
