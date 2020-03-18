package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.ParticipationInTheProject;
import com.managementservice.projectmanagement.repositorie.ParticipationInTheProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipationInTheProjectService {

    private ParticipationInTheProjectRepository participationInTheProjectRepository;

    public ParticipationInTheProjectService(ParticipationInTheProjectRepository participationInTheProjectRepository) {
        this.participationInTheProjectRepository = participationInTheProjectRepository;
    }

    public void save(ParticipationInTheProject participationInTheProject) {
        participationInTheProjectRepository.save(participationInTheProject);
    }
}
