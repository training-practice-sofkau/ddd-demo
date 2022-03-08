package co.com.sofka.domain.transporte;

import co.com.sofka.domain.generic.EventChange;
import co.com.sofka.domain.transporte.event.OrdenEntregada;
import co.com.sofka.domain.transporte.event.TransporteCreado;

import java.util.ArrayList;
import java.util.HashMap;

public class TransporteEventChange extends EventChange {
    public TransporteEventChange(Transporte transporte) {

        apply((TransporteCreado event) -> {
            transporte.conductorId = event.getConductorId();
            transporte.paquetes = new ArrayList<>();
            transporte.rutas = new ArrayList<>();
            transporte.ordenes = new HashMap<>();
        });

        apply((OrdenCreada event) -> {
            transporte.ordenes.put(
                    event.getOrdenId().value(),
                    new Orden(event.getOrdenId(), event.getRemitente(), event.getDestinatario())
            );
        });

        apply((OrdenEntregada event) -> {
            transporte.ordenes.get(event.getOrdenId().value()).entregarOrden();
        });
    }
}
