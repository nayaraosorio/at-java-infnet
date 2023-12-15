import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Throws;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.funkostore.spring.datajpa.model.FunkoPop;
import com.funkostore.spring.datajpa.repository.FunkoPopRepository;
import lombok.SneakyThrows;

class FunkoPopTest {

    @Test
    void testCreateFunkoPop() {
        
        FunkoPopController controller = new FunkoPopController(mock(FunkoPopRepository.class));

        
        FunkoPop funkoPop = new FunkoPop("Nome", "Descrição", 100.0, false);
        controller.createFunkoPop(funkoPop);

        
        mock(FunkoPopRepository).save(funkoPop);

        
        assertEquals(funkoPop, controller.createFunkoPop(funkoPop).getBody());
    }

    @SneakyThrows
    private FunkoPopRepository mock(Class<FunkoPopRepository> clazz) {
        return mock(FunkoPopRepository.class);
    }
}