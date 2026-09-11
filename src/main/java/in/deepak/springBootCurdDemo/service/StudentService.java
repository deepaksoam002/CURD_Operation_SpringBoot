package in.deepak.springBootCurdDemo.service;


import in.deepak.springBootCurdDemo.dto.*;
import in.deepak.springBootCurdDemo.entity.Student;
import in.deepak.springBootCurdDemo.exception.DuplicateResourceException;
import in.deepak.springBootCurdDemo.exception.ResourceNotFoundException;
import in.deepak.springBootCurdDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    //Instance Variable
    private final StudentRepository studentRepository;

    //Constructor
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }



    //Create Student in Database
    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentReqDto){

        Student studentReq = mapToCreateStudent(studentReqDto);
        if(checkEmailExist(studentReq)){
           throw new DuplicateResourceException("Student with email "+ studentReq.getEmail() + " already exist");
        }
        Student student = studentRepository.save(studentReq);
        return mapToCreatedResponseDto(student);
    };


    //Get Student By id
    public GetStudentResponseDto getStudent( Long id){

        Student student = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id +" not found"));
            return mapToGetStudentResponse(student);

    }


    // Get All Student List
    public List<GetStudentResponseDto> getAllStudent(){

        return studentRepository.findByDeletedIsFalse().stream()
                 .map(this::mapToGetStudentResponse)
                 .toList();

    };


    //Update Student Info In Database
    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentUpdateReq){

        Student studentToUpdate = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id +" not found"));

        studentToUpdate.setName(studentUpdateReq.getName());
        studentToUpdate.setRollNo(studentUpdateReq.getRollNo());
        studentToUpdate.setAge(studentUpdateReq.getAge());
        studentToUpdate.setSubject(studentUpdateReq.getSubject());
        studentToUpdate.setUpdatedAt(LocalDateTime.now());

        Student studentUpdatedInfo = studentRepository.save(studentToUpdate);
        return createUpdateStudentInfoResponse(studentUpdatedInfo);
    };


    //Delete Student Record From Database
    public void deleteStudent( Long id){

        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id +" not found"));

        studentRepository.delete(student);
    }


    // Mark Student Record Delete In Database
    public void softDeleteStudent( Long id){

        Student student = studentRepository
                .findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Student with id " + id +" not found"));

        student.setDeleted(true);
        studentRepository.save(student);
    }


    //CreateStudentDtoRequest --> Student Mapping
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


    //Student Object  -->  CreateStudentDtoResponse
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


    //Student Object  -->  GetStudentResponseDto
    private GetStudentResponseDto mapToGetStudentResponse(Student student){
        GetStudentResponseDto getStudentResponse = new GetStudentResponseDto();

        getStudentResponse.setId(student.getId());
        getStudentResponse.setName(student.getName());
        getStudentResponse.setAge(student.getAge());
        getStudentResponse.setEmail(student.getEmail());
        getStudentResponse.setRollNo(student.getRollNo());
        getStudentResponse.setSubject(student.getSubject());
        getStudentResponse.setCreatedAt(student.getCreatedAt());
        getStudentResponse.setUpdatedAt(student.getUpdatedAt());

        return getStudentResponse;
    }


    //Student Object  -->  UpdateStudentResponseDto
    private UpdateStudentResponseDto createUpdateStudentInfoResponse(Student student){

        UpdateStudentResponseDto updateStudentResponseDto = new UpdateStudentResponseDto();

        updateStudentResponseDto.setId(student.getId());
        updateStudentResponseDto.setName(student.getName());
        updateStudentResponseDto.setEmail(student.getEmail());
        updateStudentResponseDto.setAge(student.getAge());
        updateStudentResponseDto.setRollNo(student.getRollNo());
        updateStudentResponseDto.setSubject(student.getSubject());
        updateStudentResponseDto.setCreatedAt(student.getCreatedAt());
        updateStudentResponseDto.setUpdatedAt(student.getUpdatedAt());
        updateStudentResponseDto.setMessage("Student Updated Successfully");

        return updateStudentResponseDto;

    }

    private Boolean checkEmailExist(Student student){

        return studentRepository.existsByEmail(student.getEmail());
    }
}
