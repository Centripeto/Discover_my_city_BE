package com.boomers.www.discover_my_city.tech.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pois")
public class POIEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String description;
  private Double latitude;
  private Double longitude;
  private String status;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "creator")
  public UserEntity creator;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "approver")
  public UserEntity approver;

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public UserEntity getCreator() {
    return creator;
  }

  public void setCreator(UserEntity creator) {
    this.creator = creator;
  }

  public UserEntity getApprover() {
    return approver;
  }

  public void setApprover(UserEntity approver) {
    this.approver = approver;
  }
}