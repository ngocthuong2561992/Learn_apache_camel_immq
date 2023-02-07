package com.camelmultidb.repository.mariaDB;

import com.camelmultidb.repository.mariaDB.entity.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<FilmEntity, Integer> {
}
