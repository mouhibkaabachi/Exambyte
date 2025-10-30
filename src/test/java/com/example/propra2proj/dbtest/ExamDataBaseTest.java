package com.example.propra2proj.dbtest;

import com.example.propra2proj.infrastructurelayer.datarepository.ExamDataRepository;
import com.example.propra2proj.domain.model.examagg.Exam;
import com.example.propra2proj.domain.model.examagg.Question;
import com.example.propra2proj.domain.model.useragg.User;
import com.example.propra2proj.domain.repository.ExamRepository;
import com.example.propra2proj.infrastructurelayer.reposimplementation.ExamRepositoryImpl;
import com.example.propra2proj.web.ContainerKonfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ContainerKonfiguration.class)
public class ExamDataBaseTest {
    @Autowired
    private ExamDataRepository examDataRepository;

    private ExamRepository examRepository;

    @BeforeEach
    void setup(){
        examRepository = new ExamRepositoryImpl(examDataRepository);
    }


    @Test
    @DisplayName("Testing if we can load and save an Exam")
    public void testSaveAndLoad () throws Exception{

        //Arrange

        Question question1 =mock(Question.class);
        Question question2= mock(Question.class);
        List<Question> questions= List.of(question1,question2);
        UUID organiserID= UUID.randomUUID();
        List<UUID> studentIDs= List.of(UUID.randomUUID(), UUID.randomUUID());

        //Act

        Exam exam = new Exam(UUID.randomUUID(),"mockName",12 ,questions, organiserID, studentIDs,false );
        examRepository.save(exam);
        Optional<Exam> exam1= examRepository.findByUUID(exam.getId());

        //Assert

        assertThat(exam1.map(Exam::getAssignedStudentsId).orElseThrow()).isEqualTo(exam.getAssignedStudentsId());
        assertThat(exam1.map(Exam::getName).orElseThrow()).isEqualTo(exam.getName());
        assertThat(exam1.map(Exam::getOrganiserID).orElseThrow()).isEqualTo(exam.getOrganiserID());

    }

    @Test
    @DisplayName("Testing if we can Register for Exam ")
    public void testExamRegistry () throws Exception{

        //Arrange
        Question question1 =mock(Question.class);
        Question question2= mock(Question.class);
        List<Question> questions= List.of(question1,question2);
        UUID organiserID= UUID.randomUUID();
        List<UUID> studentIDs= List.of(UUID.randomUUID(), UUID.randomUUID());
        Exam exam = new Exam(UUID.randomUUID(),"mockName",12 ,questions, organiserID, studentIDs,false );
        User student = User.createStudent(UUID.randomUUID(),"mockMcMockito","Mockmail");
        //Act
        examRepository.ExamRegistry(exam.getId(),student.getId());

        //Assert
        assertThat(exam.getAssignedStudentsId()).contains(student.getId());

    }

    @Test
    @DisplayName("Testing if we can load and save a Question")
    public void testSaveAndLoadQuestion () throws Exception{
        //Arrange
        Question question = new Question(UUID.randomUUID(),"mockingThequestion",0,0);
        List<Question> questions= List.of();
        UUID organiserID= UUID.randomUUID();
        List<UUID> studentIDs= List.of(UUID.randomUUID(), UUID.randomUUID());
        Exam exam = new Exam(UUID.randomUUID(),"mockName",12 ,questions, organiserID, studentIDs,false );


        //Act
        examRepository.saveQuestion(exam.getId(),question);
        Optional<Question> question1= examRepository.findQuestionById(question.getId());

        //Assert

        assertThat(question1.orElseThrow()).isEqualTo(question);
        assertThat(exam.getQuestions()).contains(question);

    }

    @Test
    @DisplayName("Testing if we can accord a point to a Question")
    public void testAccordedPointsQuestion () throws Exception{
        //Arrange
        UUID MockQuestionId= UUID.randomUUID();
        Question question1=new Question(MockQuestionId,"mockingTheQuestion",2,0);

        //Act
        when(examRepository.findQuestionById(MockQuestionId)).thenReturn(Optional.of(question1));
        Question question2=new Question(question1.getId(),question1.getQuestionText(),question1.getPoints(),1);

        //Assert
        assertThat(examRepository.findQuestionById(question2.getId()).orElseThrow().getAccordedPoints()).isEqualTo(1);



    }











}
