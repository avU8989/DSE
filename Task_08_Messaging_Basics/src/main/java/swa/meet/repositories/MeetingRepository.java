package swa.meet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swa.meet.entities.Meeting;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, UUID> {
    @Query("SELECT m FROM Meeting m JOIN FETCH m.timeslots")
    //    @Query("SELECT DISTINCT m FROM Meeting m LEFT JOIN FETCH m.timeslots LEFT JOIN FETCH m.responses")
    List<Meeting> findAllWithTimeslots();
}
