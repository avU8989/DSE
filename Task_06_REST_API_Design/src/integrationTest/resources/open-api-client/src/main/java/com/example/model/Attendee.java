/*
 * Meeting Scheduler API
 * API for scheduling and managing meetings with multiple proposed time slots.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.example.model;

import java.util.Objects;
import java.util.Arrays;
import com.example.model.AttendeeAvailabilities;
import com.example.model.AttendeePerson;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Attendee
 */
@JsonPropertyOrder({
  Attendee.JSON_PROPERTY_ID,
  Attendee.JSON_PROPERTY_PERSON,
  Attendee.JSON_PROPERTY_AVAILABILITIES
})
@JsonTypeName("Attendee")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-06-04T01:04:18.517807200+02:00[Europe/Vienna]")
public class Attendee {
  public static final String JSON_PROPERTY_ID = "id";
  private UUID id;

  public static final String JSON_PROPERTY_PERSON = "person";
  private AttendeePerson person;

  public static final String JSON_PROPERTY_AVAILABILITIES = "availabilities";
  private List<AttendeeAvailabilities> availabilities = new ArrayList<>();

  public Attendee() { 
  }

  @JsonCreator
  public Attendee(
    @JsonProperty(JSON_PROPERTY_ID) UUID id
  ) {
    this();
    this.id = id;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public UUID getId() {
    return id;
  }




  public Attendee person(AttendeePerson person) {
    
    this.person = person;
    return this;
  }

   /**
   * Get person
   * @return person
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_PERSON)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public AttendeePerson getPerson() {
    return person;
  }


  @JsonProperty(JSON_PROPERTY_PERSON)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPerson(AttendeePerson person) {
    this.person = person;
  }


  public Attendee availabilities(List<AttendeeAvailabilities> availabilities) {
    
    this.availabilities = availabilities;
    return this;
  }

  public Attendee addAvailabilitiesItem(AttendeeAvailabilities availabilitiesItem) {
    this.availabilities.add(availabilitiesItem);
    return this;
  }

   /**
   * Get availabilities
   * @return availabilities
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_AVAILABILITIES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<AttendeeAvailabilities> getAvailabilities() {
    return availabilities;
  }


  @JsonProperty(JSON_PROPERTY_AVAILABILITIES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAvailabilities(List<AttendeeAvailabilities> availabilities) {
    this.availabilities = availabilities;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Attendee attendee = (Attendee) o;
    return Objects.equals(this.id, attendee.id) &&
        Objects.equals(this.person, attendee.person) &&
        Objects.equals(this.availabilities, attendee.availabilities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, person, availabilities);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Attendee {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    person: ").append(toIndentedString(person)).append("\n");
    sb.append("    availabilities: ").append(toIndentedString(availabilities)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

