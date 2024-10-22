package com.example.session_based_data;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Programmatic configuration of the server session timeout.
 * * It is stronger than the application.properties configuration.
 */
@Configuration
public class SessionConfig {

  @Bean
  HttpSessionListener httpSessionListener() {
    return new SessionListener();
  }

  private class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
      event.getSession().setMaxInactiveInterval(10);
    }

  }

}