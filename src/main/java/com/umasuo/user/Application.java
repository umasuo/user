package com.umasuo.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umasuo on 17/2/9.
 */
@SpringBootApplication(scanBasePackages = "com.umasuo")
@EnableAutoConfiguration
@Configuration
@RestController
@EnableAsync
public class Application {

  /**
   * service name.
   */
  @Value("${spring.application.name}")
  private String serviceName;

  /**
   * This api is used for health check.
   *
   * @return service name.
   */
  @GetMapping("/")
  public String index() {
    JsonObject json = new JsonObject();
    json.addProperty("service", serviceName);
    json.addProperty("systemTime", System.currentTimeMillis());
    return json.toString();
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
