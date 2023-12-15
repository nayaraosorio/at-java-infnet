package com.funkostore.spring.datajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funkostore.spring.datajpa.model.FunkoPop;

public interface FunkoPopRepository extends JpaRepository<FunkoPop, Long> {
	List<FunkoPop> findByAvailable(boolean available);
	List<FunkoPop> findByTitleContaining(String title);
}
