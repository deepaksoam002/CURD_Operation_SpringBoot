package in.deepak.springBootCurdDemo.service;


import in.deepak.springBootCurdDemo.dto.*;
import in.deepak.springBootCurdDemo.entity.Student;
import in.deepak.springBootCurdDemo.repository.StudentRepository;
import org.springframework.stereotype.Component;
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

        Student student = studentRepository.save(studentReq);
        return mapToCreatedResponseDto(student);
    };


    //Get Student By id
    public GetStudentResponseDto getStudent( Long id){

        Optional<Student> studentRes = studentRepository.findByIdAndDeletedIsFalse(id);

        if(studentRes.isPresent()){

            Student student = studentRes.get();
            return mapToGetStudentResponse(student);
        };

        return null;
    }


    // Get All Student List
    public List<GetStudentResponseDto> getAllStudent(){

        return studentRepository.findByDeletedIsFalse().stream()
                 .map(this::mapToGetStudentResponse)
                 .toList();

    };


    //Update Student Info In Database
    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentUpdateReq){

        Optional<Student>  getStudentInfo = studentRepository.findByIdAndDeletedIsFalse(id);

        if(getStudentInfo.isEmpty()){
            return null;
        }

        Student studentToUpdate = getStudentInfo.get();
        studentToUpdate.setName(studentUpdateReq.getName());
        studentToUpdate.setRollNo(studentUpdateReq.getRollNo());
        studentToUpdate.setAge(studentUpdateReq.getAge());
        studentToUpdate.setSubject(studentUpdateReq.getSubject());
        studentToUpdate.setUpdatedAt(LocalDateTime.now());

        Student studentUpdatedInfo = studentRepository.save(studentToUpdate);
        return createUpdateStudentInfoResponse(studentUpdatedInfo);
    };


    //Delete Student Record From Database
    public Boolean deleteStudent( Long id){

        boolean isStudentExist = studentRepository.existsById(id);
        if(!isStudentExist){
            return null;
        }
        studentRepository.deleteById(id);
        return true;
    }


    // Mark Student Record Delete In Database
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
}
