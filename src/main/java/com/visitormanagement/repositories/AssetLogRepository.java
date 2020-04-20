package com.visitormanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.AssetLog;

@Repository
public interface AssetLogRepository extends JpaRepository<AssetLog, Long>{

}
