package ssvv.lab2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */

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
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
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
}
