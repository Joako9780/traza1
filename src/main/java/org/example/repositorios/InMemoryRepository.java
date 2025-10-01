package org.example.repositorios;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryRepository<T> {
    protected Map<Long, T> data = new HashMap<>();      //La clave es de tipo Long, ID unico para el obj, T es el valor del mapa
    protected AtomicLong idGenerator = new AtomicLong();       //Genera IDs por mas de que sea multihilo, atomicLong es inicializada en 0 por def.
    //Util en BD
    public T save(T entity) {
        long id = idGenerator.incrementAndGet();    //genera el id y se los asigna a la variable id
        // Suponiendo que las entidades tienen un metodo setId
        try {
            String clase;
            entity.getClass().getMethod("setId", Long.class).invoke(entity, id);
            clase = entity.getClass().getName();
            System.out.println(clase + "   id :" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        data.put(id, entity);   //put es como el add en un ArrayList
        return entity;
    }

    public Optional<T> findById(Long id) {      //Evita que se cancele el programa si el puntero es NULL, lo que busco es NULL? lo puedo almacenar como vacio, cuando tenga valor se lo asigno
        return Optional.ofNullable(data.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(data.values());  //values trae todos los elementos del mapa y los asigna al ArrayList en el momento de la creacion
    }

    public Optional<T> genericUpdate(Long id, T updatedEntity) {    //Busco algo y si existe lo actualizo con este metodo
        if (!data.containsKey(id)) {
            return Optional.empty();
        }
        try {
            // Establecer el mismo ID en la entidad actualizada para mantener la coherencia
            Method setIdMethod = updatedEntity.getClass().getMethod("setId", Long.class);
            setIdMethod.invoke(updatedEntity, id);

            data.put(id, updatedEntity);    //hace un enlace entre este ID con el ID que tenía en el HashMap y reemplaza, solapa la informacion el UpdateEntity
            return Optional.of(updatedEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<T> genericDelete(Long id) {
        if (!data.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.ofNullable(data.remove(id));
    }

    public List<T> genericFindByField(String fieldName, Object value) {
        List<T> results = new ArrayList<>();
        try {
            for (T entity : data.values()) {
                Method getFieldMethod = entity.getClass().getMethod("get" + capitalize(fieldName));
                Object fieldValue = getFieldMethod.invoke(entity);
                if (fieldValue != null && fieldValue.equals(value)) {
                    results.add(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);

        //con substring de la posicion 0 me corro 1 a la derecha, la convierto en mayuscula con UpperCase, substring obtiene el resto de la cadena
    }
}
