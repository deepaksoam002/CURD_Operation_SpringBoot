package in.deepak.springBootCurdDemo.controller;

import in.deepak.springBootCurdDemo.dto.*;
import in.deepak.springBootCurdDemo.entity.Student;
import in.deepak.springBootCurdDemo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<CreateStudentResponseDto> createStudent(@Valid @RequestBody CreateStudentRequestDto studentRequestDto){
          CreateStudentResponseDto createdStudentDto = studentService.createStudent(studentRequestDto);
          return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(createdStudentDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetStudentResponseDto> getStudent(@PathVariable Long id ){
        GetStudentResponseDto student = studentService.getStudent(id);
         return ResponseEntity
                 .ok(student);
    }

    @GetMapping
    public ResponseEntity<List<GetStudentResponseDto>> getAllStudent(){
        List<GetStudentResponseDto> studentList = studentService.getAllStudent();
        return ResponseEntity.ok(studentList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateStudentResponseDto> updateStudentInfo(@PathVariable Long id, @RequestBody UpdateStudentRequestDto student){
        UpdateStudentResponseDto updatedStudent = studentService.updateStudent(id,student);
        return ResponseEntity.status(HttpStatus.OK).body(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentInfo(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Student info removed successfully");
    }

    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<String> softDeleteStudentInfo(@PathVariable Long id){
        studentService.softDeleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Student info deleted successfully");
    }

}
