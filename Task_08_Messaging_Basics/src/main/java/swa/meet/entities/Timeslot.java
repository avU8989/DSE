package swa.meet.entities;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;


import javax.annotation.Generated;

/**
 * Timeslot
 */

@Entity
@Data
@Table(name = "timeslots")
public class Timeslot extends RepresentationModel<Timeslot> implements Serializable {
  @Id
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @NotNull
  private java.time.LocalDateTime start;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @NotNull
  @Column(name = "`end`")
  private java.time.LocalDateTime end;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "meeting_id", nullable = false)
  @JsonBackReference
  private Meeting meeting;

  public Timeslot() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Timeslot(java.time.LocalDateTime start, java.time.LocalDateTime end, Meeting meeting) {
    this.start = start;
    this.end = end;
    this.meeting = meeting;
  }

  public Timeslot id(UUID id) {
    this.id = id;
    return this;
  }

  public void setMeeting(Meeting meeting) {
    this.meeting = meeting;
  }

  public Timeslot meeting(Meeting meeting) {
    this.meeting = meeting;
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

  public Timeslot start(java.time.LocalDateTime start) {
    this.start = start;
    return this;
  }

  /**
   * Get start
   * @return start
  */
  @NotNull @Valid 
  @Schema(name = "start", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("start")
  public java.time.LocalDateTime getStart() {
    return start;
  }

  public void setStart(java.time.LocalDateTime start) {
    this.start = start;
  }

  public Timeslot end(java.time.LocalDateTime end) {
    this.end = end;
    return this;
  }

  /**
   * Get end
   * @return end
  */
  @NotNull @Valid 
  @Schema(name = "end", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("end")
  public java.time.LocalDateTime getEnd() {
    return end;
  }

  public void setEnd(java.time.LocalDateTime end) {
    this.end = end;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Timeslot timeslot = (Timeslot) o;
    return Objects.equals(this.id, timeslot.id) &&
        Objects.equals(this.start, timeslot.start) &&
        Objects.equals(this.end, timeslot.end);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, start, end);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Timeslot {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    start: ").append(toIndentedString(start)).append("\n");
    sb.append("    end: ").append(toIndentedString(end)).append("\n");
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

