package tec.bd.app.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tec.bd.app.dao.CursoDAO;
import tec.bd.app.domain.Curso;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class CursoServiceImplTest {

    @Mock
    CursoDAO cursoDAO;

    @InjectMocks
    CursoServiceImpl cursoService;

    @Test
    public void getByDepartment() throws Exception {

        List<Curso> cursos = Arrays.asList(mock(Curso.class), mock(Curso.class));

        given(cursoDAO.findByDepartment(anyString())).willReturn(cursos);

        // Aqui estamos haciendo el TEST
        var actual = this.cursoService.getByDepartment("Biologia");

        verify(cursoDAO, times(1)).findByDepartment("Biologia");

        assertThat(actual).hasSize(2);
    }


}
