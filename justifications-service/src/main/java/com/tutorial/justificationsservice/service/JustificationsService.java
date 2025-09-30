package com.tutorial.justificationsservice.service;

import com.tutorial.justificationsservice.entity.JustificationsEntity;
import com.tutorial.justificationsservice.repository.JustificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JustificationsService {
    @Autowired
    JustificationsRepository justificationsRepository;

    public ArrayList<JustificationsEntity> getJustifications(){
        return (ArrayList<JustificationsEntity>) justificationsRepository.findAll();
    }

    public JustificationsEntity saveJustification(JustificationsEntity justification){return justificationsRepository.save(justification);
    }
}