package swa.meet.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;


import javax.annotation.Generated;
import javax.validation.constraints.NotNull;

/**
 * AttendeeAvailabilitiesInner
 */
@Entity
@Data
@JsonTypeName("Availability")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-06-03T13:56:05.584535900+02:00[Europe/Vienna]")
public class Availability extends RepresentationModel<Availability> implements Serializable {

  @Id
  @Column(columnDefinition = "BINARY(16)")
  private UUID slotId;

  /**
   * Gets or Sets availability
   */
  public enum AvailabilityEnum {
    YES("yes"),
    
    NO("no"),
    
    MAYBE("maybe");

    private String value;

    AvailabilityEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AvailabilityEnum fromValue(String value) {
      for (AvailabilityEnum b : AvailabilityEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "availability", nullable = false)
  private AvailabilityEnum availability;

  @NotNull
  @Column(name = "time_slot_id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID timeslotId;

  public Availability slotId(UUID slotId) {
    this.slotId = slotId;
    return this;
  }

  /**
   * Get slotId
   * @return slotId
  */
  @Valid 
  @Schema(name = "slotId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("slotId")
  public UUID getSlotId() {
    return slotId;
  }

  public void setSlotId(UUID slotId) {
    this.slotId = slotId;
  }

  public Availability availability(AvailabilityEnum availability) {
    this.availability = availability;
    return this;
  }

  /**
   * Get availability
   * @return availability
  */
  
  @Schema(name = "availability", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("availability")
  public AvailabilityEnum getAvailability() {
    return availability;
  }

  public void setAvailability(AvailabilityEnum availability) {
    this.availability = availability;
  }


  /**
   * Get timeslots
   *
   * @return timeslots
   */
  @Valid
  @Schema(name = "timeslotId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("timeslotId")
  public UUID getTimeslotId() {
    return timeslotId;
  }

  public void setTimeslotId(UUID timeslotId) {
    this.timeslotId = timeslotId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Availability availability = (Availability) o;
    return Objects.equals(this.slotId, availability.slotId) &&
        Objects.equals(this.availability, availability.availability);
  }

  @Override
  public int hashCode() {
    return Objects.hash(slotId, availability);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AttendeeAvailabilitiesInner {\n");
    sb.append("    slotId: ").append(toIndentedString(slotId)).append("\n");
    sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
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

