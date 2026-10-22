package com.college.studentmanagement.service;

import com.college.studentmanagement.entity.Student;
import com.college.studentmanagement.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        checkRequiredFields(student);

        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        student.setId(null); // make sure a new row is created
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found with id " + id));
    }

    public Student updateStudent(Long id, Student updated) {
        checkRequiredFields(updated);

        Student existing = getStudentById(id);

        // Email must not belong to a different student
        Optional<Student> sameEmail = studentRepository.findByEmail(updated.getEmail());
        if (sameEmail.isPresent() && !sameEmail.get().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setBranch(updated.getBranch());
        existing.setYear(updated.getYear());
        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        Student existing = getStudentById(id);
        studentRepository.delete(existing);
    }

    private void checkRequiredFields(Student student) {
        if (student.getName() == null || student.getName().isBlank()
                || student.getEmail() == null || student.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name and email are required");
        }
    }
}
