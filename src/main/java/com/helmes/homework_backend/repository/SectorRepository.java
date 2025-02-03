package com.helmes.homework_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.helmes.homework_backend.entity.Sector;

public interface SectorRepository extends JpaRepository<Sector, Integer> {
}
