package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.maiko26.springboot.model.Session;
import tn.maiko26.springboot.model.Task;
import tn.maiko26.springboot.model.User;
import tn.maiko26.springboot.model.relations.StarredTask;

import java.util.Optional;

public interface StarredTaskRepository extends JpaRepository<StarredTask, String> {
    Optional<StarredTask> findByTaskAndUser(Task task, User user);

}
