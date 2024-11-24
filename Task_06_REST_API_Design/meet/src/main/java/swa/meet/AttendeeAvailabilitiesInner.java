package swa.meet;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * AttendeeAvailabilitiesInner
 */

@JsonTypeName("Attendee_availabilities_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-06-03T13:56:05.584535900+02:00[Europe/Vienna]")
public class AttendeeAvailabilitiesInner {

  private UUID slotId;

  /**
   * Gets or Sets availability
   */
  public enum AvailabilityEnum {
    TRUE("true"),
    
    FALSE("false"),
    
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

  private AvailabilityEnum availability;

  public AttendeeAvailabilitiesInner slotId(UUID slotId) {
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

  public AttendeeAvailabilitiesInner availability(AvailabilityEnum availability) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AttendeeAvailabilitiesInner attendeeAvailabilitiesInner = (AttendeeAvailabilitiesInner) o;
    return Objects.equals(this.slotId, attendeeAvailabilitiesInner.slotId) &&
        Objects.equals(this.availability, attendeeAvailabilitiesInner.availability);
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

