package swa.meet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swa.meet.entities.Timeslot;

import java.util.List;
import java.util.UUID;

public interface TimeslotRepository extends JpaRepository<Timeslot, UUID> {
    List<Timeslot> findByMeetingId(UUID uuid);
}
