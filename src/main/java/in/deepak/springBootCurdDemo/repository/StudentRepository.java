package in.deepak.springBootCurdDemo.repository;

import in.deepak.springBootCurdDemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Long> {


    Optional<Student> findByIdAndDeletedIsFalse(Long id);
    List<Student>findByDeletedIsFalse();
    Boolean existsByEmail(String email);
}
