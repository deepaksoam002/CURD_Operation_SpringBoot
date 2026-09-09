package in.deepak.springBootCurdDemo.service;


import in.deepak.springBootCurdDemo.dto.CreateStudentRequestDto;
import in.deepak.springBootCurdDemo.dto.CreateStudentResponseDto;
import in.deepak.springBootCurdDemo.entity.Student;
import in.deepak.springBootCurdDemo.repository.StudentRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentReqDto){

        Student studentReq = mapToCreateStudent(studentReqDto);

        Student student = studentRepository.save(studentReq);
        return mapToCreatedResponseDto(student);
    };

    public Student getStudent( Long id){

        Optional<Student> studentRes = studentRepository.findByIdAndDeletedIsFalse(id);

        if(studentRes.isPresent()){
            return studentRes.get();
        };

        return null;
    }

    public List<Student> getAllStudent(){

        return studentRepository.findByDeletedIsFalse();
    };

    public Student updateStudent( Long id, Student student){

        Optional<Student>  getStudentInfo = studentRepository.findByIdAndDeletedIsFalse(id);

        if(getStudentInfo.isEmpty()){
            return null;
        }

        Student studentToUpdate = getStudentInfo.get();
        studentToUpdate.setName(student.getName());
        studentToUpdate.setEmail(student.getEmail());
        studentToUpdate.setRollNo(student.getRollNo());
        studentToUpdate.setAge(student.getAge());
        studentToUpdate.setSubject(student.getSubject());


        return studentRepository.save(studentToUpdate);
    };

    public Boolean deleteStudent( Long id){

        boolean isStudentExist = studentRepository.existsById(id);

        if(!isStudentExist){
            return null;
        }

        studentRepository.deleteById(id);
        return true;
    }

    public Boolean softDeleteStudent( Long id){

        Optional<Student> isStudentExist = studentRepository.findById(id);

        if(isStudentExist.isEmpty()){
            return null;
        }

        Student student = isStudentExist.get();
        student.setDeleted(true);
        studentRepository.save(student);
        return true;
    }

    private Student mapToCreateStudent(CreateStudentRequestDto studentRequestDto){
        Student student = new Student();

        student.setName(studentRequestDto.getName());
        student.setEmail(studentRequestDto.getEmail());
        student.setAge(studentRequestDto.getAge());
        student.setRollNo(studentRequestDto.getRollNo());
        student.setSubject(studentRequestDto.getSubject());
        student.setDeleted(false);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        return student;
    }

    private CreateStudentResponseDto mapToCreatedResponseDto(Student student){
        CreateStudentResponseDto createStudentResponseDto = new CreateStudentResponseDto();

        createStudentResponseDto.setId(student.getId());
        createStudentResponseDto.setName(student.getName());
        createStudentResponseDto.setAge(student.getAge());
        createStudentResponseDto.setEmail(student.getEmail());
        createStudentResponseDto.setRollNo(student.getRollNo());
        createStudentResponseDto.setSubject(student.getSubject());
        createStudentResponseDto.setMessage("Student created Successfully");

        return createStudentResponseDto;
    }
}
