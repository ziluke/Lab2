package ssvv.lab2;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    StudentXMLRepository fileRepository1;
    TemaXMLRepository fileRepository2;
    NotaXMLRepository fileRepository3;

    Service service;

    @Before
    public void setup() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        fileRepository1 = new StudentXMLRepository(studentValidator, "src/test/studenti.xml");
        fileRepository2 = new TemaXMLRepository(temaValidator, "src/test/teme.xml");
        fileRepository3 = new NotaXMLRepository(notaValidator, "src/test/note.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @After
    public void tearDown() {
        try {
            String defaultFileContent = new String(Files.readAllBytes(Paths.get("src/test/gol.xml")), StandardCharsets.UTF_8);

            PrintWriter printWriter = new PrintWriter("src/test/studenti.xml");

            printWriter.print(defaultFileContent);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tc_1_addStudent(){
        int aux = service.saveStudent("33", "New Student 3", 934);
        assertEquals(1, aux);

        Student stud = fileRepository1.findOne("33");
        assertEquals("New Student 3", stud.getNume());
    }

    @Test(expected = ValidationException.class)
    public void tc_2_addStudent(){
        Validator<Student> studentValidator = new StudentValidator();
        StudentRepository repository = new StudentRepository(studentValidator);
        repository.save(new Student("", "New Student 3", 934));
    }

    //Group
    @Test(expected = ValidationException.class)
    public void tc_3_addStudent(){
        Validator<Student> studentValidator = new StudentValidator();
        StudentRepository repository = new StudentRepository(studentValidator);
        repository.save(new Student("33", "New Student 3", 939));
    }

    @Test(expected = ValidationException.class)
    public void tc_4_addStudent(){
        Validator<Student> studentValidator = new StudentValidator();
        StudentRepository repository = new StudentRepository(studentValidator);
        repository.save(new Student("33", "New Student 3", 109));
    }

    @Test
    public void tc_5_addStudent(){
        int aux = service.saveStudent("33", "New Student 3", 934);
        assertEquals(1, aux);
    }

    ///Name
    @Test
    public void tc_6_addStudent(){
        int aux = service.saveStudent("33", "", 934);
        assertEquals(0, aux);
    }

    @Test
    public void tc_7_addStudent(){
        int aux = service.saveStudent("33", null, 934);
        assertEquals(0, aux);
    }

    @Test
    public void tc_8_addStudent(){
        int aux = service.saveStudent("33", "Name1", 934);
        assertEquals(1, aux);
    }

    @Test
    public void tc_9_addStudent(){
        int aux = service.saveStudent(null, "Name1", 924);
        assertEquals(0, aux);
    }

    @Test
    public void tc_10_addStudent(){
        int aux = service.saveStudent("33", "Name1", 934);
        assertEquals(1, aux);

        aux = service.saveStudent("33", "Name2", 936);
        assertEquals(0, aux);
    }

    @Test
    public void tc_11_addStudent(){
        int aux = service.saveStudent("33", "Name1", 934);
        assertFalse(aux < 0);
        assertFalse(aux > 1);
    }

    @Test
    public void tc_12_addStudent(){
        int aux = service.saveStudent(")", "Name1", 934);
        assertEquals(1, aux);
    }

    @Test
    public void tc_13_addStudent(){
        int aux = service.saveStudent("33", "%", 934);
        assertEquals(1, aux);
    }

    @Test
    public void tc_14_addStudent(){
        int aux = service.saveStudent("33", "Name1", 111);
        assertEquals(1, aux);
    }

    @Test
    public void tc_15_addStudent(){
        int aux = service.saveStudent("33", "Name1", 937);
        assertEquals(1, aux);
    }

    @Test
    public void tc_16_addStudent(){
        int aux = service.saveStudent("33", "Name1", 110);
        assertEquals(0, aux);
    }

    @Test
    public void tc_17_addStudent(){
        int aux = service.saveStudent("33", "Name1", 112);
        assertEquals(1, aux);
    }

    @Test
    public void tc_18_addStudent(){
        int aux = service.saveStudent("33", "Name1", 938);
        assertEquals(0, aux);
    }

    @Test
    public void tc_19_addStudent(){
        int aux = service.saveStudent("33", "Name1", 936);
        assertEquals(1, aux);
    }
}
