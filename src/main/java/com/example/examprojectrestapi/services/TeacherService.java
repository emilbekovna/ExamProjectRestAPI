package com.example.examprojectrestapi.services;

import com.example.examprojectrestapi.dto.SimpleResponse;
import com.example.examprojectrestapi.dto.student.StudentResponse;
import com.example.examprojectrestapi.dto.teacher.TeacherRequest;
import com.example.examprojectrestapi.dto.teacher.TeacherResponse;
import com.example.examprojectrestapi.exceptions.StudentNotFoundException;
import com.example.examprojectrestapi.exceptions.TeacherNotFoundException;
import com.example.examprojectrestapi.mappers.edits.TeacherEditMapper;
import com.example.examprojectrestapi.mappers.views.TeacherViewMapper;
import com.example.examprojectrestapi.models.Student;
import com.example.examprojectrestapi.models.Teacher;
import com.example.examprojectrestapi.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    private final TeacherViewMapper teacherViewMapper;

    private final TeacherEditMapper teacherEditMapper;


//    private Teacher getTeacherById(Long teacherId) {
//        return teacherRepository.findById(teacherId).orElseThrow(
//                () -> new TeacherNotFoundException(teacherId)
//        );
//    }

    //find all
    public List<TeacherResponse> findAll() {
        return teacherViewMapper.view(teacherRepository.findAll());
    }

   // save
    public TeacherResponse save(TeacherRequest teacherRequest) {
        Teacher teacher = teacherEditMapper.create(teacherRequest);
        teacherRepository.save(teacher);
        return teacherViewMapper.viewCompany(teacher);
    }

    //find by id
    public TeacherResponse findById(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                () -> new TeacherNotFoundException("My exception: Couldn't not found teacher!")
        );
        return teacherViewMapper.viewCompany(teacher);
    }

    //delete by id
    public SimpleResponse deleteById(Long teacherId) {

        boolean exists = teacherRepository.existsById(teacherId);

        if(!exists) {
            throw new TeacherNotFoundException("My exception: Couldn't not found teacher!");
        }
        teacherRepository.deleteById(teacherId);

        return new SimpleResponse(
                "DELETED",
                "Successfully deleted student!"
        );
    }

    //update by id
    public TeacherResponse updateById(Long teacherId, TeacherRequest teacherRequest) {
        Teacher teacher = teacherRepository.findById(teacherId).get();
        teacherEditMapper.update(teacher,teacherRequest);
        teacherRepository.save(teacher);
        return teacherViewMapper.viewCompany(teacherRepository.save(teacher));
    }


}
