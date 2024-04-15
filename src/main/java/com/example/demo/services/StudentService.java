package com.example.demo.services;

import com.example.demo.DTOs.response.StudentResDTO;
import com.example.demo.models.Account;
import com.example.demo.models.Major;
import com.example.demo.models.Student;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Student getStudentInfo(@NonNull HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getUserPrincipal().getName();
        return studentRepository.findByAccountUsername(username);
    }

    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    public List<Student> getAllActiveStudents() {
        return studentRepository.findByDeletedFalse();
    }

    public Student updateStudent(Long id, StudentResDTO.GetStudentInfoResDTO updatedStudentInfo) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setStudentCode(updatedStudentInfo.studentCode());
            student.setFullName(updatedStudentInfo.fullName());
            student.setGender(updatedStudentInfo.gender());
            student.setBirthday(updatedStudentInfo.birthday());
            student.setIdcard(updatedStudentInfo.idcard());
            student.setPhone(updatedStudentInfo.phone());
            student.setMajor(updatedStudentInfo.major());
            return studentRepository.save(student);
        } else {
            return null;
        }
    }

    public Student hiddenStudent(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            Long accountId = student.getAccount().getId();

            student.setDeleted(true);
            studentRepository.save(student);

            Optional<Account> optionalAccount = accountRepository.findById(accountId);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                account.setDeleted(true);
                accountRepository.save(account);
            }

            return student;
        } else {
            return null;
        }
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student findStudentByAccountID(Long id) {
        return studentRepository.findByAccountID(id);
    }

}
