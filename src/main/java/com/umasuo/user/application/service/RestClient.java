package com.umasuo.user.application.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.user.application.dto.DeviceView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by Davis on 17/6/12.
 */
@Component
public class RestClient {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RestClient.class);

  /**
   * The Device url.
   */
  @Value("${device.service.url:http://device/")
  private String deviceUrl;

  /**
   * The Rest template.
   */
  private RestTemplate restTemplate = new RestTemplate();

  /**
   * Gets device by user.
   *
   * @param deviceDefinitionId the device definition id
   * @param userId the user id
   * @param developerId the developer id
   * @return the device by user
   */
  public DeviceView getDeviceByUser(String deviceDefinitionId, String userId, String developerId) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(deviceUrl)
        .queryParam("userId", userId)
        .queryParam("deviceDefinitionId", deviceDefinitionId);

    HttpEntity entity = new HttpEntity(headers);
    DeviceView result = null;
    try {

      HttpEntity<DeviceView> response = restTemplate.exchange(
          builder.build().encode().toUriString(),
          HttpMethod.GET,
          entity,
          DeviceView.class);

      result = response.getBody();
    } catch (InvalidMediaTypeException invalidMediaTypeException) {
      LOG.info("Can not find Device: ", invalidMediaTypeException);
      throw new NotExistException("Can not find Device");
    }
    return result;
  }

}
