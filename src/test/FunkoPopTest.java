import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Throws;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.funkostore.spring.datajpa.model.FunkoPop;
import com.funkostore.spring.datajpa.repository.FunkoPopRepository;
import lombok.SneakyThrows;

class FunkoPopTest {

    @Test
    void testCreateFunkoPop() {
        FunkoPopRepository repositoryMock = mock(FunkoPopRepository.class);
    
        when(repositoryMock.save(Mockito.any(FunkoPop.class))).thenReturn(new FunkoPop("Nome", "Descrição", 100.0, false));
    
        FunkoPopController controller = new FunkoPopController(repositoryMock);
    
        FunkoPop funkoPop = new FunkoPop("Nome", "Descrição", 100.0, false);
    
        when(repositoryMock.save(funkoPop)).thenThrow(FunkoPopCreationException.class);
    
        assertThrows(FunkoPopCreationException.class, () -> {
            controller.createFunkoPop(funkoPop);
        });
    
        Mockito.verify(repositoryMock, Mockito.times(1)).save(funkoPop);
    }
    
}