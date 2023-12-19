package com.boomers.www.discover_my_city;

import com.boomers.www.discover_my_city.model.POI;
import com.boomers.www.discover_my_city.service.POIService;

import java.util.UUID;

public class Contributor {
  private final String id;
  private final POIService poiService;
  private String name;
  private String surname;
  private String email;

  public Contributor(String name, String surname, String email, POIService poiService) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.poiService = poiService;
  }

  public POI createPOI(String name, String description, Coordinate coordinate) {
    POI poi = new POI(name, description, coordinate);
    return poiService.create(poi);
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
