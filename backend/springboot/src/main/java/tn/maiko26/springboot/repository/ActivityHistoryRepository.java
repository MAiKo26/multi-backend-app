package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.maiko26.springboot.model.ActivityHistory;
import tn.maiko26.springboot.model.Session;

import java.util.List;

public interface ActivityHistoryRepository extends JpaRepository<ActivityHistory, String> {
    @Query("SELECT a FROM ActivityHistory a LEFT JOIN FETCH a.user ORDER BY a.doneAt")
    List<ActivityHistory> findAllWithUsersOrderByDoneAt();

    @Query("SELECT a FROM ActivityHistory a LEFT JOIN FETCH a.user WHERE a.user.email = :email ORDER BY a.doneAt")
    List<ActivityHistory> findAllWithUsersOrderByDoneAtWhereUser(@Param("email") String email);


}
