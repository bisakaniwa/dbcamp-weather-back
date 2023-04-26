package com.template.data.repository;

import com.template.data.entity.MeteorologiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeteorologiaRepository extends JpaRepository<MeteorologiaEntity, Long> {

}
