package org.example;

import org.example.entidades.*;
import org.example.repositorios.InMemoryRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        InMemoryRepository<Empresa> empresaRepository = new InMemoryRepository<>();

        System.out.println(" ===== COMIENZA EJECUCION ===== ");

        Pais argentina = Pais.builder()
                .nombre("Argentina")
                .build();

        Provincia p1 = Provincia.builder()
                .nombre("Buenos Aires")
                .pais(argentina)
                .build();

        Provincia p2 = Provincia.builder()
                .nombre("Cordoba")
                .pais(argentina)
                .build();

        Localidad l1 = Localidad.builder()
                .nombre("CABA")
                .provincia(p1)
                .build();

        Localidad l2 = Localidad.builder()
                .nombre("La plata")
                .provincia(p1)
                .build();

        Localidad l3 = Localidad.builder()
                .nombre("Cordoba Capital")
                .provincia(p2)
                .build();

        Localidad l4 = Localidad.builder()
                .nombre("Villa Carlos Paz")
                .provincia(p2)
                .build();

        Domicilio d1 = Domicilio.builder()
                .calle("Florencio Varela")
                .numero(376)
                .cp(4410)
                .localidad(l1)
                .build();

        Domicilio d2 = Domicilio.builder()
                .localidad(l2)
                .calle("Calle 7")
                .numero(1100)
                .cp(1900)
                .build();

        Domicilio d3 = Domicilio.builder()
                .localidad(l3)
                .calle("25 de Mayo")
                .numero(123)
                .cp(5000)
                .build();

        Domicilio d4 = Domicilio.builder()
                .localidad(l4)
                .calle("Avenida San Martin")
                .numero(500)
                .cp(5152)
                .build();

        Sucursal s1 = Sucursal.builder()
                .nombre("Limpito")
                .horarioApertura(LocalTime.of(9, 0))
                .horarioCierre(LocalTime.of(20, 0))
                .domicilio(d1)
                .build();

        Sucursal s2 = Sucursal.builder()
                .nombre("Limpito Emporio de la Limpieza")
                .horarioApertura(LocalTime.of(8, 0))
                .horarioCierre(LocalTime.of(19, 0))
                .domicilio(d2)
                .build();

        Sucursal s3 = Sucursal.builder()
                .nombre("Limpito Productos de Limpieza")
                .horarioApertura(LocalTime.of(9, 0))
                .horarioCierre(LocalTime.of(19, 0))
                .domicilio(d3)
                .build();

        Sucursal s4 = Sucursal.builder()
                .nombre("Limpito!")
                .horarioApertura(LocalTime.of(8, 0))
                .horarioCierre(LocalTime.of(20, 0))
                .domicilio(d4)
                .build();

        Empresa e1 = Empresa.builder()
                .nombre("Limpito Emporio de la Limpieza Buenos Aires")
                .razonSocial("Limpito S.A")
                .cuit(592380071L)
                .logo("Cuidamos tu hogar")
                .build();

        Empresa e2 = Empresa.builder()
                .nombre("Limpito Emporio de la Limpieza Cordoba")
                .razonSocial("Limpito S.A")
                .cuit(592380071L)
                .logo("Animate a limpiar ahorrando")
                .build();

        e1.agregarSucursal(s1);
        e1.agregarSucursal(s2);

        e2.agregarSucursal(s3);
        e2.agregarSucursal(s4);

        //Punto 5
        //a. Mostrar todas las empresas

        System.out.println("\n ===== SE GUARDAN LAS EMPRESAS ===== ");
        empresaRepository.save(e1);
        empresaRepository.save(e2);

        System.out.println("\n ===== SE MUESTRAN TODAS LAS EMPRESAS ===== ");
        List<Empresa> listaEmpresas = empresaRepository.findAll();
        System.out.println("Las empresas guardadas son: " + listaEmpresas);
        listaEmpresas.forEach(System.out::println);

        //b. Buscar una empresa por su Id.

        System.out.println("\n ===== BUSCAR EMPRESAS POR ID ===== ");
        Optional<Empresa> empresaEncontrada = empresaRepository.findById(1L);
        empresaEncontrada.ifPresent(e -> System.out.println("Empresa encontrada por ID 1: " + e));

        //c. Buscar empresar por su nombre

        System.out.println("\n ===== BUSCAR EMPRESAS POR SU NOMBRE ===== ");
        List<Empresa> empresasPorNombre = empresaRepository.genericFindByField("nombre", "Empresa 1");
        System.out.println("Empresas con nombre 'Empresa 1':");
        empresasPorNombre.forEach(System.out::println);

        //d. Actualizar los datos de una empresa buscando por su ID. Por ejemplo modificar su cuil.

        System.out.println("\n ===== ACTUALIZACION DE DATOS EMPRESA 1 ===== ");
        Empresa empresaActualizada = Empresa.builder()
                .id(1L)
                .nombre("Empresa 1 Actualizada")
                .razonSocial("Razon Social 1 Actualizada")
                .cuit(12345678901L)
                .sucursales(e1.getSucursales())
                .build();

        empresaRepository.genericUpdate(1L, empresaActualizada);
        Optional<Empresa> empresaVerificada = empresaRepository.findById(1L);
        empresaVerificada.ifPresent(e -> System.out.println("Empresa después de la actualización: " + e));

        //e. Eliminar una empresa buscando por su ID.

        System.out.println("\n ===== ELIMINACION DE EMPRESA POR ID 1 ===== ");
        empresaRepository.genericDelete(1L);
        Optional<Empresa> empresaEliminada = empresaRepository.findById(1L);
        if (empresaEliminada.isEmpty()) {
            System.out.println("La empresa con ID 1 ha sido eliminada.");
        }

        // Mostrar todas las empresas restantes
        System.out.println("Todas las empresas después de la eliminación:");
        List<Empresa> empresasRestantes = empresaRepository.findAll();
        empresasRestantes.forEach(System.out::println);
    }
}