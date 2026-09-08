package in.deepak.springBootCurdDemo.controller;

import in.deepak.springBootCurdDemo.entity.Student;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @PostMapping
    public void createStudent(@RequestBody Student student){

    }

    public Student getStudent(){

    }
}
