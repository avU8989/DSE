package swa.meet;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import swa.meet.Attendee;
import swa.meet.Timeslot;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Meeting
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-06-03T13:56:05.584535900+02:00[Europe/Vienna]")
public class Meeting {

  private UUID id;

  private String scheduleName;

  private String description;

  private String creator;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private java.time.LocalDateTime created;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private java.time.LocalDateTime validUntil;

  @Valid
  private List<@Valid Timeslot> timeslots;

  @Valid
  private List<@Valid Attendee> responses;

  public Meeting() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Meeting(String scheduleName, String creator, java.time.LocalDateTime created, java.time.LocalDateTime validUntil) {
    this.scheduleName = scheduleName;
    this.creator = creator;
    this.created = created;
    this.validUntil = validUntil;
  }

  public Meeting id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @Valid 
  @Schema(name = "id", accessMode = Schema.AccessMode.READ_ONLY, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Meeting scheduleName(String scheduleName) {
    this.scheduleName = scheduleName;
    return this;
  }

  /**
   * Get scheduleName
   * @return scheduleName
  */
  @NotNull 
  @Schema(name = "scheduleName", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("scheduleName")
  public String getScheduleName() {
    return scheduleName;
  }

  public void setScheduleName(String scheduleName) {
    this.scheduleName = scheduleName;
  }

  public Meeting description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Meeting creator(String creator) {
    this.creator = creator;
    return this;
  }

  /**
   * Get creator
   * @return creator
  */
  @NotNull 
  @Schema(name = "creator", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("creator")
  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public Meeting created(java.time.LocalDateTime created) {
    this.created = created;
    return this;
  }

  /**
   * Get created
   * @return created
  */
  @NotNull @Valid 
  @Schema(name = "created", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("created")
  public java.time.LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(java.time.LocalDateTime created) {
    this.created = created;
  }

  public Meeting validUntil(java.time.LocalDateTime validUntil) {
    this.validUntil = validUntil;
    return this;
  }

  /**
   * Get validUntil
   * @return validUntil
  */
  @NotNull @Valid 
  @Schema(name = "validUntil", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("validUntil")
  public java.time.LocalDateTime getValidUntil() {
    return validUntil;
  }

  public void setValidUntil(java.time.LocalDateTime validUntil) {
    this.validUntil = validUntil;
  }

  public Meeting timeslots(List<@Valid Timeslot> timeslots) {
    this.timeslots = timeslots;
    return this;
  }

  public Meeting addTimeslotsItem(Timeslot timeslotsItem) {
    if (this.timeslots == null) {
      this.timeslots = new ArrayList<>();
    }
    this.timeslots.add(timeslotsItem);
    return this;
  }

  /**
   * Get timeslots
   * @return timeslots
  */
  @Valid 
  @Schema(name = "timeslots", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("timeslots")
  public List<@Valid Timeslot> getTimeslots() {
    return timeslots;
  }

  public void setTimeslots(List<@Valid Timeslot> timeslots) {
    this.timeslots = timeslots;
  }

  public Meeting responses(List<@Valid Attendee> responses) {
    this.responses = responses;
    return this;
  }

  public Meeting addResponsesItem(Attendee responsesItem) {
    if (this.responses == null) {
      this.responses = new ArrayList<>();
    }
    this.responses.add(responsesItem);
    return this;
  }

  /**
   * Get responses
   * @return responses
  */
  @Valid 
  @Schema(name = "responses", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("responses")
  public List<@Valid Attendee> getResponses() {
    return responses;
  }

  public void setResponses(List<@Valid Attendee> responses) {
    this.responses = responses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Meeting meeting = (Meeting) o;
    return Objects.equals(this.id, meeting.id) &&
        Objects.equals(this.scheduleName, meeting.scheduleName) &&
        Objects.equals(this.description, meeting.description) &&
        Objects.equals(this.creator, meeting.creator) &&
        Objects.equals(this.created, meeting.created) &&
        Objects.equals(this.validUntil, meeting.validUntil) &&
        Objects.equals(this.timeslots, meeting.timeslots) &&
        Objects.equals(this.responses, meeting.responses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, scheduleName, description, creator, created, validUntil, timeslots, responses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Meeting {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    scheduleName: ").append(toIndentedString(scheduleName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    validUntil: ").append(toIndentedString(validUntil)).append("\n");
    sb.append("    timeslots: ").append(toIndentedString(timeslots)).append("\n");
    sb.append("    responses: ").append(toIndentedString(responses)).append("\n");
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

