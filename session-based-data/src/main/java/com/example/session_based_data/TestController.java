
package com.example.session_based_data;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/dialer")
@RequiredArgsConstructor
public class TestController {

  @GetMapping("/dial")
  public String dial(@RequestParam String phone, HttpSession session) {
    if (session.getAttribute("connection_d") != null)
      return "You already in a call.";

    UUID connectionId = UUID.randomUUID();
    session.setAttribute("connection_d", connectionId);
    log.info("Generated Connection ID: {}", connectionId);

    return "Call started...";
  }

  @GetMapping("/hangup")
  public String hangup(HttpSession session) {
    if (session.getAttribute("connection_d") == null)
      return "You didn't start a call to hang it up.";

    UUID connectionId = (UUID) session.getAttribute("connection_d");
    session.removeAttribute("connection_d");
    log.info("Terminated Connection ID: {}", connectionId);

    return "Call hanged up:";
  }

}
