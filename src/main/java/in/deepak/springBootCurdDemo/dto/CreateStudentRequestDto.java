package in.deepak.springBootCurdDemo.dto;

import jakarta.validation.constraints.*;

public class CreateStudentRequestDto {

    @NotEmpty(message = "Name is required")
    @Size(min = 2, max = 100)
    private String name;

    @Email(message = "Email is required and correct")
    private String email;

    @Positive
    @Max(100)
    private int rollNo;
    @Min(5)
    private int age;
    @NotEmpty
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
