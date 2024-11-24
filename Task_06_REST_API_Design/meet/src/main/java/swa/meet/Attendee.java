package swa.meet;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import swa.meet.AttendeeAvailabilitiesInner;
import swa.meet.AttendeePerson;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Attendee
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-06-03T13:56:05.584535900+02:00[Europe/Vienna]")
public class Attendee {

  private UUID id;

  private AttendeePerson person;

  @Valid
  private List<@Valid AttendeeAvailabilitiesInner> availabilities = new ArrayList<>();

  public Attendee() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Attendee(AttendeePerson person, List<@Valid AttendeeAvailabilitiesInner> availabilities) {
    this.person = person;
    this.availabilities = availabilities;
  }

  public Attendee id(UUID id) {
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

  public Attendee person(AttendeePerson person) {
    this.person = person;
    return this;
  }

  /**
   * Get person
   * @return person
  */
  @NotNull @Valid 
  @Schema(name = "person", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("person")
  public AttendeePerson getPerson() {
    return person;
  }

  public void setPerson(AttendeePerson person) {
    this.person = person;
  }

  public Attendee availabilities(List<@Valid AttendeeAvailabilitiesInner> availabilities) {
    this.availabilities = availabilities;
    return this;
  }

  public Attendee addAvailabilitiesItem(AttendeeAvailabilitiesInner availabilitiesItem) {
    if (this.availabilities == null) {
      this.availabilities = new ArrayList<>();
    }
    this.availabilities.add(availabilitiesItem);
    return this;
  }

  /**
   * Get availabilities
   * @return availabilities
  */
  @NotNull @Valid 
  @Schema(name = "availabilities", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("availabilities")
  public List<@Valid AttendeeAvailabilitiesInner> getAvailabilities() {
    return availabilities;
  }

  public void setAvailabilities(List<@Valid AttendeeAvailabilitiesInner> availabilities) {
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

