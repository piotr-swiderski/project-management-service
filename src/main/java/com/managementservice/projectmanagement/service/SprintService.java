package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.repositorie.SprintRepository;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
public class SprintService {

    private SprintRepository sprintRepository;

    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public Sprint saveSprint(Sprint sprint){
        return sprintRepository.save(sprint);
    }

    public Sprint getSprintById(Long id) {
        return sprintRepository.findById(id).orElseThrow(NoResultException::new);
    }
}
