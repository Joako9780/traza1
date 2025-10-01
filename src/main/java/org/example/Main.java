package org.example;

import org.example.entidades.Empresa;
import org.example.entidades.Sucursal;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Sucursal sucursal1 = Sucursal.builder()
                .nombre("Sucursal 1")
                .horarioApertura(LocalTime.of(9, 0))
                .horarioCierre(LocalTime.of(18, 0))
                .esCasaMatriz(true)
                .build();

        Sucursal sucursal2 = Sucursal.builder()
                .nombre("Sucursal 2")
                .horarioApertura(LocalTime.of(9, 0))
                .horarioCierre(LocalTime.of(18, 0))
                .esCasaMatriz(false)
                .build();

        Empresa empresa = Empresa.builder()
                .cuit(12345678901L)
                .nombre("Empresa 1")
                .razonSocial("Razón Social")
                .sucursales(new HashSet<>(Set.of(sucursal1, sucursal2)))
                .build();

        empresa.toString();
    }
}