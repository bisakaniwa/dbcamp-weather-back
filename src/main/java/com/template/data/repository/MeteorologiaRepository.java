package com.template.data.repository;

import com.template.data.entity.MeteorologiaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeteorologiaRepository extends JpaRepository<MeteorologiaEntity, Long> {

    List<MeteorologiaEntity> findByCidade(String cidade);

    List<MeteorologiaEntity> findByCidadeAndDataBetween(String cidade, LocalDate amanha, LocalDate ultimoDia);

}
