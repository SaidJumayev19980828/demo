package com.era.tofate.repository.aboutapp;

import com.era.tofate.entities.aboutapp.AboutApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutAppRepository extends JpaRepository<AboutApp, Long> {
}
