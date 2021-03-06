package co.com.sofka.usecases;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.transporte.Transporte;
import co.com.sofka.domain.transporte.command.CrearOrden;

public class CrearOrdenUseCase extends UseCase<RequestCommand<CrearOrden>, ResponseEvents> {

    @Override
    public void executeUseCase(RequestCommand<CrearOrden> input) {
        var command = input.getCommand();

        var transporte = Transporte.from(command.getTransporteId(), retrieveEvents());
        if(transporte.ordenes().values().size() >= 10){//regla de negocio
            throw new BusinessException(command.getTransporteId().value(), "No se puede agregar mas de 10 ordenes por transportador");
        }
        transporte.crearOrden(command.getOrdenId(), command.getRemitente(),command.getDestinatario());
        emit().onResponse(new ResponseEvents(transporte.getUncommittedChanges()));
    }
}
