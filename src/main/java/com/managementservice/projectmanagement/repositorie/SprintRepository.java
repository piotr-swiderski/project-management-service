package com.managementservice.projectmanagement.repositorie;

import com.managementservice.projectmanagement.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Integer> {
}
