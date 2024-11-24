package swa.meet.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import swa.meet.entities.Timeslot;
import swa.meet.exceptions.ResourceNotFoundException;
import swa.meet.repositories.MeetingRepository;
import swa.meet.repositories.TimeslotRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TimeslotService {

    private final TimeslotRepository timeslotRepository;
    private final MeetingRepository meetingRepository;

    public TimeslotService(MeetingRepository meetingRepository, TimeslotRepository timeslotRepository) {
        this.meetingRepository = meetingRepository;
        this.timeslotRepository = timeslotRepository;
    }

    public Timeslot saveTimeslot(Timeslot timeslot){
        return timeslotRepository.save(timeslot);
    }

    public List<Timeslot> saveAllTimeslots(List<Timeslot> timeslot){
        return timeslotRepository.saveAll(timeslot);
    }

    public Timeslot getTimeslotById(UUID id) {
        return timeslotRepository.findById(id).orElse(null);
    }

    public void deleteTimeslot(UUID timeslot){
        timeslotRepository.deleteById(timeslot);
    }

    public List<Timeslot> getTimeslotsByMeetingId(UUID meetingID) {

        if (!meetingRepository.existsById(meetingID)) {
            throw new ResourceNotFoundException("Meeting not found with id: " + meetingID);
        }
        return timeslotRepository.findByMeetingId(meetingID);
    }
}
