package in.deepak.springBootCurdDemo.controller;

import in.deepak.springBootCurdDemo.entity.Student;
import in.deepak.springBootCurdDemo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;


    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
          Student createdStudent = studentService.createStudent(student);

          return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") Long id){

        Student student = studentService.getStudent(id);

         return ResponseEntity
                 .ok(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudent(){

        List<Student> studentList = studentService.getAllStudent();
        return ResponseEntity.ok(studentList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudentInfo(@PathVariable Long id, @RequestBody Student student){

        Student updatedStudent = studentService.updateStudent(id,student);

        if(updatedStudent == null){

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedStudent);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentInfo(@PathVariable Long id){

        Boolean isDeleted = studentService.deleteStudent(id);

        if(isDeleted == null){

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Student deleted Successfully");
    }

}
