package swa.meet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swa.meet.entities.Person;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
