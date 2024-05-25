package com.example.reparacionservice.Repository;

import com.example.reparacionservice.Entity.ReparacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReparacionRepository extends JpaRepository<ReparacionEntity,Long> {

}
