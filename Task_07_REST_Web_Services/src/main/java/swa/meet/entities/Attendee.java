package swa.meet.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import javax.annotation.Generated;

/**
 * Attendee
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-06-03T13:56:05.584535900+02:00[Europe/Vienna]")
@Entity
@Data
public class Attendee extends RepresentationModel<Attendee>{
  @Id
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @OneToOne
  @NotNull
  private Person person;

  @Valid
  @ElementCollection
  @CollectionTable(name = "attendee_availability", joinColumns = @JoinColumn(name = "attendee_id"))
  @Column(name = "availability")
  private List<@Valid Availability> availabilities = new ArrayList<>();

  @ManyToMany(mappedBy = "responses")
  @JsonBackReference
  private List<Meeting> meetings;

  public Attendee() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Attendee(Person person, List<@Valid Availability> availabilities, List<Meeting> meetings) {
    this.person = person;
    this.availabilities = availabilities;
    this.meetings = meetings;
  }

  public Attendee(Person person, List<@Valid Availability> availabilities) {
    this.person = person;
    this.availabilities = availabilities;
    this.meetings = new ArrayList<>();
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

  public Attendee person(Person person) {
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
  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Attendee availabilities(List<@Valid Availability> availabilities) {
    this.availabilities = availabilities;
    return this;
  }

  public Attendee addAvailabilitiesItem(Availability availabilitiesItem) {
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
  public List<@Valid Availability> getAvailabilities() {
    return availabilities;
  }

  public void setAvailabilities(List<@Valid Availability> availabilities) {
    this.availabilities = availabilities;
  }

  /**
   * Get meetings
   * @return meetings
   */
  @NotNull @Valid
  @Schema(name = "meetings", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("meetings")
  public List<@Valid Meeting> getMeetings() {
    return meetings;
  }

  public void setMeetings(List<@Valid Meeting> meetings) {
    this.meetings = meetings;
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

