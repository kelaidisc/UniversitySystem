package com.kelaidisc.controller;

import com.kelaidisc.converter.student.StudentCreateDtoToStudent;
import com.kelaidisc.converter.student.StudentUpdateDtoToStudent;
import com.kelaidisc.domain.Student;
import com.kelaidisc.dto.DeleteDto;
import com.kelaidisc.dto.student.StudentCreateDto;
import com.kelaidisc.dto.student.StudentUpdateDto;
import com.kelaidisc.exception.UniversityBadRequestException;
import com.kelaidisc.model.StudentSearchField;
import com.kelaidisc.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentCreateDtoToStudent studentCreateDtoToStudent;
    private final StudentUpdateDtoToStudent studentUpdateDtoToStudent;

    @GetMapping
    public List<Student> findAll(@RequestParam(value = "studentSearchField", required = false) StudentSearchField studentSearchField,
                                 @RequestParam(value = "searchTerm", required = false) String searchTerm) {
        if (nonNull(studentSearchField)) {
            return studentService.search(studentSearchField, searchTerm);
        }
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public Student findById(@NotNull @Positive @PathVariable("id") Long id) {
        return studentService.findOrThrow(id);
    }

    @PostMapping
    public Student create(@Valid @RequestBody StudentCreateDto student) {
        return studentService.create(Objects.requireNonNull(studentCreateDtoToStudent.convert(student)));
    }

    @PutMapping("/{id}")
    public Student update(@NotNull @Positive @PathVariable("id") Long id, @Valid @RequestBody StudentUpdateDto student) {

        if(!Objects.equals(student.getId(), id)){
            throw new UniversityBadRequestException(Student.class, "id",
                    "Must be the same as the path variable that is used");
        }
        return studentService.update(Objects.requireNonNull(studentUpdateDtoToStudent.convert(student)));
    }

    @DeleteMapping
    public void delete(@Valid @RequestBody DeleteDto deleteDto) {
        studentService.deleteByIds(deleteDto.getIds());
    }

















}
