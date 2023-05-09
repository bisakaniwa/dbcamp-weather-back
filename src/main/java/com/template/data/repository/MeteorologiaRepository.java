package com.template.data.repository;

import com.template.data.entity.MeteorologiaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeteorologiaRepository extends JpaRepository<MeteorologiaEntity, Long> {

    @Override
    Page<MeteorologiaEntity> findAll(Pageable pageable);

    List<MeteorologiaEntity> findByCidade(String cidade);

    Page<MeteorologiaEntity> findByCidade(Pageable pageable, String cidade);

}
