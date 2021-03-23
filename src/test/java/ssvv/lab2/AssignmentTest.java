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
import validation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class AssignmentTest {
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

            PrintWriter printWriter = new PrintWriter("src/test/teme.xml");

            printWriter.print(defaultFileContent);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tc_1_addAssignment(){
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

    @Test(expected = ValidationException.class)
    public void tc_2_addAssignment(){
        service.saveTema("1", "Desc", 0, 8);
    }
}
