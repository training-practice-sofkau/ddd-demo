package co.com.sofka.usecases;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.transporte.event.OrdenCreada;
import co.com.sofka.domain.transporte.command.CrearOrden;
import co.com.sofka.domain.transporte.event.TransporteCreado;
import co.com.sofka.domain.transporte.valor.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CrearOrdenUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Test
    void crearOrden(){
        //arrange
        var transporteId = TransporteId.of("xxxx");
        var ordenId = OrdenId.of("ffffff");
        var remitente = new Remitente("Raul", "Alzate", "xxxxxx");
        var destinatario = new Destinatario("Pedro", "Sanchez", "fffff");
        var command = new CrearOrden( transporteId,  ordenId,  remitente,  destinatario);


        var usecase = new CrearOrdenUseCase();
        Mockito.when(repository.getEventsBy("xxxx")).thenReturn(history());
        usecase.addRepository(repository);
        //act

        var events = UseCaseHandler.getInstance()
                .setIdentifyExecutor(transporteId.value())
                .syncExecutor(usecase, new RequestCommand<>(command))
                .orElseThrow()
                .getDomainEvents();
        //assert

        var event = (OrdenCreada) events.get(0);
        Assertions.assertEquals("transporte.ordencreada", event.type);
        Assertions.assertEquals("ffffff", event.getOrdenId().value());
        //TODO: colocar las demas validaciones
    }

    private List<DomainEvent> history() {
        return List.of(
                new TransporteCreado(ConductorId.of("xxxx")),
                new OrdenCreada(OrdenId.of("1"), null, null),
                new OrdenCreada(OrdenId.of("2"), null, null),
                new OrdenCreada(OrdenId.of("3"), null, null),
                new OrdenCreada(OrdenId.of("4"), null, null),
                new OrdenCreada(OrdenId.of("5"), null, null),
                new OrdenCreada(OrdenId.of("6"), null, null),
                new OrdenCreada(OrdenId.of("7"), null, null),
                new OrdenCreada(OrdenId.of("8"), null, null),
                new OrdenCreada(OrdenId.of("9"), null, null),
                new OrdenCreada(OrdenId.of("10"), null, null)
        );
    }

}