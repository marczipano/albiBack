package hu.albi.back;

import hu.albi.back.model.Sublet;
import hu.albi.back.repo.SubletRepository;
import hu.albi.back.service.SubletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ServiceTests {

    @InjectMocks
    private SubletService subletService;

    @Mock
    SubletRepository subletRepository;

    @Test()
    void deleteNotOwnSublet() {
        Sublet sublet = new Sublet();
        sublet.setSellerId(10);

        doReturn(sublet).when(subletRepository).findSubletById(anyInt());
        Assertions.assertThrows(Exception.class, () -> SubletService.deleteSubletByUser(1, 20));
    }

    @Test()
    void deleteOwnSublet() {
        Sublet sublet = new Sublet();
        sublet.setSellerId(10);

        doReturn(sublet).when(subletRepository).findSubletById(anyInt());
        Assertions.assertDoesNotThrow(() -> SubletService.deleteSubletByUser(1, sublet.getSellerId())
        );
    }
}
