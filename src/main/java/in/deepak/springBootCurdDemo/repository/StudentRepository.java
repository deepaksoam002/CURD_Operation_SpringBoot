package in.deepak.springBootCurdDemo.repository;

import in.deepak.springBootCurdDemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


public interface StudentRepository extends JpaRepository<Student, Long> {

}
