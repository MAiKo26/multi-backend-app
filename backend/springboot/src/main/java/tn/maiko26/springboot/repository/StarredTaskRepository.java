package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.maiko26.springboot.model.Session;
import tn.maiko26.springboot.model.relations.StarredTask;

import java.util.Optional;

public interface StarredTaskRepository extends JpaRepository<StarredTask, String> {
    Optional<StarredTask> findByTaskIdAndUserId(String taskId, String userId);

}
