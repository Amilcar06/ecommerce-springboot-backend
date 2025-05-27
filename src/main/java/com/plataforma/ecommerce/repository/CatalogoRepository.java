package com.plataforma.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plataforma.ecommerce.model.Catalogo;

public interface CatalogoRepository extends JpaRepository<Catalogo, Long>{
  
    Boolean existsByCatalogo(long id_catalogo);
    
} 
