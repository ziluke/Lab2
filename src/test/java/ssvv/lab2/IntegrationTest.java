package ssvv.lab2;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
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

            PrintWriter printWriter = new PrintWriter("src/test/note.xml");
            printWriter.print(defaultFileContent);
            printWriter.close();

            printWriter = new PrintWriter("src/test/studenti.xml");
            printWriter.print(defaultFileContent);
            printWriter.close();

            printWriter = new PrintWriter("src/test/teme.xml");
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

    @Test
    public void tc_2_addAssignment(){
        service.saveTema("1", "Desc", 10, 8);
        int ok = 0;
        for (Tema tema:
                service.findAllTeme()) {
            if(tema.getID().equals("1")){
                assertEquals("Desc", tema.getDescriere());
                ok = 1;
                break;
            }
        }

        assertEquals(1, ok);
    }

    @Test
    public void tc_3_addGrade(){
        int aux = service.saveNota("33", "1", 9, 3, "Nice work");
        assertEquals(-1, aux);
    }

    @Test
    public void tc_4_addAll(){
        int aux = service.saveStudent("33", "New Student 3", 934);
        int aux2 = service.saveTema("1", "Desc", 10, 8);
        int aux3 = service.saveNota("33", "1", 9, 3, "Nice work");

        assertEquals(1, aux3);
    }
}
