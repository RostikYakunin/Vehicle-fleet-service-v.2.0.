package org.example.repo;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface Crud<E>{
    boolean add (E e,Connection connection);
    boolean deleteById(Integer id, Connection connection);
    Optional<E> getById (Integer id, Connection connection);
    boolean updateById (Integer id, Connection connection);
    List<E> getAll (Connection connection);
}
