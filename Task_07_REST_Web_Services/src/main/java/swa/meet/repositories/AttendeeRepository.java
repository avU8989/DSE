package swa.meet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swa.meet.entities.Attendee;

import java.util.UUID;

public interface AttendeeRepository extends JpaRepository<Attendee, UUID> {
}
