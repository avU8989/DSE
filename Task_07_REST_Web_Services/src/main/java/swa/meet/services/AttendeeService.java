package swa.meet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swa.meet.entities.Attendee;
import swa.meet.entities.Availability;
import swa.meet.entities.Meeting;
import swa.meet.entities.Person;
import swa.meet.exceptions.AlreadySubmittedAttendanceException;
import swa.meet.exceptions.EmptyAvailabilitiesException;
import swa.meet.exceptions.InvalidTimeslotException;
import swa.meet.repositories.AttendeeRepository;
import swa.meet.repositories.AvailabilityRepository;
import swa.meet.repositories.PersonRepository;
import swa.meet.repositories.TimeslotRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttendeeService {
    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TimeslotRepository timeslotRepository;

    @Transactional
    public Attendee saveAttendee(Attendee attendee, Meeting meeting) {
        if (attendee.getAvailabilities().isEmpty()) {
            throw new EmptyAvailabilitiesException("Availabilities should not be empty!");
        }

        boolean isExistingAttendee = false;
        for (Attendee existingAttendee : meeting.getResponses()) {
            if (existingAttendee.getId().equals(attendee.getId())) {
                existingAttendee.setAvailabilities(attendee.getAvailabilities());
                isExistingAttendee = true;
                break;
            }
        }
        if (!isExistingAttendee) {
            List<Availability> validAvailabilities = new ArrayList<>();
            for (Availability availability : attendee.getAvailabilities()) {
                if (!timeslotRepository.existsById(availability.getTimeslotId())) {
                    throw new InvalidTimeslotException("A timeslot with this given id does not exist: " + availability.getTimeslotId());
                }
                if (!availabilityRepository.existsById(availability.getSlotId())) {
                    validAvailabilities.add(availability);
                }
            }

            availabilityRepository.saveAll(validAvailabilities);

            // Ensure person is saved and linked correctly
            Person person = attendee.getPerson();
            personRepository.save(person);

            // Link attendee to meeting
            attendee.getMeetings().add(meeting);
            meeting.getResponses().add(attendee);
        }

        return attendeeRepository.save(attendee);
    }

    public Optional<Attendee> getAttendeeById(UUID uuid) {
        return this.attendeeRepository.findById(uuid);
    }
}
