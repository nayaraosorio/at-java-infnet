import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Throws;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bezkoder.spring.datajpa.model.FunkoPop;
import com.bezkoder.spring.datajpa.repository.FunkoPopRepository;
import lombok.SneakyThrows;

class FunkoPopTest {

    @Test
    void testCreateFunkoPop() {
        // Crie um objeto FunkoPopController com dependências mockadas
        FunkoPopController controller = new FunkoPopController(mock(FunkoPopRepository.class));

        // Crie um objeto FunkoPop e verifique se o método createFunkoPop funciona corretamente
        FunkoPop funkoPop = new FunkoPop("Nome", "Descrição", 100.0, false);
        controller.createFunkoPop(funkoPop);

        // Verifique se o método save foi chamado com os argumentos corretos
        mock(FunkoPopRepository).save(funkoPop);

        // Verifique se o objeto FunkoPop retornado pelo método createFunkoPop é o mesmo que o objeto FunkoPop criado
        assertEquals(funkoPop, controller.createFunkoPop(funkoPop).getBody());
    }

    @SneakyThrows
    private FunkoPopRepository mock(Class<FunkoPopRepository> clazz) {
        return mock(FunkoPopRepository.class);
    }
}