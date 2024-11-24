package swa.meet.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swa.meet.entities.Attendee;
import swa.meet.entities.Meeting;
import swa.meet.entities.Timeslot;
import swa.meet.exceptions.ResourceNotFoundException;
import swa.meet.repositories.MeetingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private static final Logger logger = LoggerFactory.getLogger(MeetingService.class);

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Transactional
    public List<Meeting> getAllMeetings() {
        //very weird, lazy loading problem even with annotation eager fetching
        //wihtout calling a seperate queue the timeslots are not initialized
        List<Meeting> meetings = meetingRepository.findAllWithTimeslots();

        return meetingRepository.findAll();
    }

    public Optional<Meeting> getMeetingById(UUID id) {
        Optional<Meeting> meeting = meetingRepository.findById(id);
        if (!meeting.isPresent()) {
            throw new ResourceNotFoundException("Meeting not found with id: " + id);
        }
        return meeting;
    }

    public List<Attendee> getMeetingSubmissions(UUID meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + meetingId));

        return meeting.getResponses();
    }

    @Transactional
    public Meeting saveMeeting(Meeting meeting) {
        for (Timeslot timeslot : meeting.getTimeslots()) {
            timeslot.setMeeting(meeting);
        }

        return meetingRepository.save(meeting);
    }

    public void checkForExistingMeetingId(UUID meetingID){
        if (meetingRepository.existsById(meetingID)) {
            throw new DataIntegrityViolationException("A meeting with this ID already exists: " + meetingID.toString());
        }
    }

    @Transactional
    public Meeting updateMeeting(UUID meetingId, Meeting meetingDetails) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + meetingId));

        meeting.setScheduleName(meetingDetails.getScheduleName());
        meeting.setDescription(meetingDetails.getDescription());
        meeting.setCreator(meetingDetails.getCreator());
        meeting.setCreated(meetingDetails.getCreated());
        meeting.setValidUntil(meetingDetails.getValidUntil());

        meeting.getTimeslots().clear();
        for (Timeslot timeslot : meetingDetails.getTimeslots()) {
            timeslot.setMeeting(meeting);
            meeting.getTimeslots().add(timeslot);
        }

        meeting.setResponses(meetingDetails.getResponses());

        return meetingRepository.save(meeting);
    }


    @Transactional
    public void deleteMeeting(UUID id) {
        if (!meetingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Meeting not found with id: " + id);
        }

        meetingRepository.deleteById(id);
    }

    public Meeting addTimeslot(UUID meetingId, Timeslot timeslot) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + meetingId));
        meeting.getTimeslots().add(timeslot);
        return meetingRepository.save(meeting);
    }

    public Meeting removeTimeslot(UUID meetingId, UUID timeslotId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + meetingId));
        meeting.getTimeslots().removeIf(ts -> ts.getId().equals(timeslotId));
        return meetingRepository.save(meeting);
    }

}
