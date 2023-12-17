package com.github.sanchezih.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.sanchezih.agenda.model.Contacto;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

}