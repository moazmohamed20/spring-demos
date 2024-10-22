package com.istnetworks.soap_demo.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.istnetworks.soap_demo.entity.User;
import com.istnetworks.soap_demo.repository.UserRepository;
import com.istnetworks.user.GetUserRequest;
import com.istnetworks.user.GetUserResponse;

@Endpoint
public class UserEndpoints {
  private static final String NAMESPACE_URI = "http://istnetworks.com/user";

  @Autowired
  private UserRepository userRepository;

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
  @ResponsePayload
  public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
    GetUserResponse response = new GetUserResponse();

    User user = userRepository.findById(request.getId()).orElse(null);
    if (user != null) {
      com.istnetworks.user.User userResponse = new com.istnetworks.user.User();
      userResponse.setId(user.getId());
      userResponse.setName(user.getName());
      userResponse.setEmail(user.getEmail());
      response.setUser(userResponse);
    }

    return response;
  }

}
