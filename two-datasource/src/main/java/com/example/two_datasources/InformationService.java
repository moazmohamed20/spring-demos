package com.example.two_datasources;

import com.example.two_datasources.egain.repository.EGainUserRepository;
import com.example.two_datasources.local.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InformationService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final EGainUserRepository eGainUserRepository;


    public InformationService(UserRepository userRepository,EGainUserRepository eGainUserRepository) {
        this.userRepository = userRepository;
        this.eGainUserRepository = eGainUserRepository;
    }

    @PostConstruct
    public void info() {
    log.info("Information Service");
    log.info(userRepository.findAll().toString());
    log.info(eGainUserRepository.findAll().toString());
    log.info("Information Service Complete");
}

}
