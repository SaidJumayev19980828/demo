package com.era.tofate.repository.preloaderlabel;

import com.era.tofate.entities.preloaderlabel.PreloaderLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreloaderLabelRepository extends JpaRepository<PreloaderLabel, Long> {
}
