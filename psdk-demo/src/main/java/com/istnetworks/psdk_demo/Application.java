package com.istnetworks.psdk_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.istnetworks.psdk_demo.service.CfgService;
import com.istnetworks.psdk_demo.service.UcsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("Application started");
	}

	@Autowired
	private UcsService ucs;

	@Autowired
	private CfgService cfg;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		cfg.openConnection();
		cfg.releaseConnection();

		ucs.openConnection();
		ucs.releaseConnection();
	}

}
