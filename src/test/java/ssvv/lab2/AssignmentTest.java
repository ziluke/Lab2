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

    @Test
    public void tc_2_addAssignment(){
        int aux = service.saveTema("1", "Desc", 0, 8);
        assertEquals(0, aux);
    }

    @Test
    public void tc_3_addAssignment(){
        int aux = service.saveTema("", "Desc", 4, 1);
        assertEquals(0, aux);
    }

    @Test
    public void tc_4_addAssignment(){
        int aux = service.saveTema(null, "Desc", 4, 1);
        assertEquals(0, aux);
    }

    @Test
    public void tc_5_addAssignment(){
        int aux = service.saveTema("1", "", 4, 1);
        assertEquals(0, aux);
    }

    @Test
    public void tc_6_addAssignment(){
        int aux = service.saveTema("1", null, 4, 1);
        assertEquals(0, aux);
    }

    @Test
    public void tc_7_addAssignment(){
        int aux = service.saveTema("1", "Desc", 15, 1);
        assertEquals(0, aux);
    }

    @Test
    public void tc_8_addAssignment(){
        int aux = service.saveTema("1", "Desc", 2, 4);
        assertEquals(0, aux);
    }

    @Test
    public void tc_9_addAssignment(){
        int aux = service.saveTema("1", "Desc", 15, 5);
        assertEquals(0, aux);
    }

    @Test
    public void tc_10_addAssignment(){
        int aux = service.saveTema("1", "Desc", 5, 5);
        assertEquals(1, aux);
    }

    @Test
    public void tc_11_addAssignment(){
        int aux = service.saveTema("1", "Desc", 14, 1);
        assertEquals(1, aux);
    }

    @Test
    public void tc_12_addAssignment(){
        int aux = service.saveTema("1", "Desc", 1, 15);
        assertEquals(0, aux);
    }
}
