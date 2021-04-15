package com.era.tofate.repository.socialstatus;

import com.era.tofate.entities.socialstatus.SocialStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialStatusRepository  extends JpaRepository<SocialStatus, Long> {
}
