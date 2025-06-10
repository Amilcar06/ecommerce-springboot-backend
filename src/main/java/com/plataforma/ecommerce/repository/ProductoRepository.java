package com.plataforma.ecommerce.repository;

import com.plataforma.ecommerce.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaId(Long categoriaId);
    Page<Producto> findByCategoriaId(Long categoriaId, Pageable pageable);
    
    List<Producto> findByCategoria_Tienda_Id(Long tiendaId);
    Page<Producto> findByCategoria_Tienda_Id(Long tiendaId, Pageable pageable);
}
