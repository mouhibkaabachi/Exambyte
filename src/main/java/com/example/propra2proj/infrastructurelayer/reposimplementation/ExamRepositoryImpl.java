package com.example.propra2proj.infrastructurelayer.reposimplementation;

import com.example.propra2proj.infrastructurelayer.datarepository.UserDataRepository;
import com.example.propra2proj.applicationlayer.dtos.examagg.ExamDTO;
import com.example.propra2proj.infrastructurelayer.datarepository.ExamDataRepository;

import com.example.propra2proj.applicationlayer.exceptions.ExamResultNotFoundException;
import com.example.propra2proj.domain.model.examagg.Question;
import com.example.propra2proj.domain.repository.ExamRepository;

import com.example.propra2proj.domain.model.examagg.Exam;
import com.example.propra2proj.applicationlayer.mapper.ExamMapper;

import com.example.propra2proj.applicationlayer.mapper.QuestionMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ExamRepositoryImpl  implements ExamRepository {

  private final ExamDataRepository examDataRepository;
  private final ExamMapper examMapper = new ExamMapper() ;


    private UserDataRepository userDataRepository;



    public ExamRepositoryImpl(ExamDataRepository examDataRepository) {
        this.examDataRepository = examDataRepository;


    }

    @Override
    public Collection<Exam> findAll() {
        return examDataRepository.findAll()
                .stream()
                .map(this.examMapper::toexam)
                .toList();
    }

    @Override
    public Optional<Exam> findByUUID(UUID id) {
        return examDataRepository.findByUuid(id)
                .map(this.examMapper::toexam);
    }

    @Override
    public List<Exam> findByOrganiserID(UUID id) {
        return examDataRepository.findByOrganiserID(id).stream().map(examMapper::toexam).toList();
    }


    @Override
    public List<Exam> findByStudentID(UUID StudentsID) {
        List<Exam> examList = new ArrayList<>();
        examDataRepository.findAll().stream().map(ExamDTO::AssignedStudentsId).forEach(x->x.stream().
                forEach(y->{if (y==StudentsID)
        {examList.add(examMapper.toexam(examDataRepository.findByUuid(y).get()));
        }
        }));
   return examList; }

    @Override
    public void save(Exam exam) {
        Integer DbKey= examDataRepository.findByUuid(exam.getId()).map(ExamDTO::id).orElse(null);
        examDataRepository.save(examMapper.toexamDTO(exam, DbKey)) ;
    }


    @Override
    public void ExamRegistry(UUID examID, UUID newStudentID) {
        ExamDTO examDTO= examDataRepository.findByUuid(examID).orElseThrow(ExamResultNotFoundException::new);

        examDTO.AssignedStudentsId().add(newStudentID);
        examDataRepository.save(examDTO)  ;
    }

    @Override
    public void DeleteStudentFromExam(UUID examID, UUID StudentID) {
        ExamDTO examDTO= examDataRepository.findByUuid(examID).orElseThrow(ExamResultNotFoundException::new);
        examDTO.AssignedStudentsId().remove(userDataRepository.findByUuid(StudentID));
        examDataRepository.save(examDTO);
    }

    @Override
    public Optional<Question> findQuestionById(UUID id) {
        Exam exam= examMapper.toexam(Objects.requireNonNull(examDataRepository.findByUuid(id).orElse(null)));

        return exam.getQuestions().stream().filter(x->x.getId()==id).findFirst();
    }

    @Override
    public void saveQuestion(UUID ExamID,Question question) {
        Exam exam= examMapper.toexam(Objects.requireNonNull(examDataRepository.findByUuid(ExamID).orElse(null)));
        exam.getQuestions().add(question);
    }

    @Override
    public void deleteQuestion(UUID ExamID,Question question) {
        Exam exam= examMapper.toexam(Objects.requireNonNull(examDataRepository.findByUuid(ExamID).orElse(null)));
        exam.getQuestions().remove(question);

    }

    @Override
    public List<Question> findAllQuestions(UUID ExamID) {
        return  examDataRepository.findByUuid(ExamID).get().questions().stream().map(QuestionMapper::toQuestion).toList();
    }




}






