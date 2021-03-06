package co.com.sofka.domain.transporte;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.transporte.event.DestinatorioCambiado;
import co.com.sofka.domain.transporte.event.OrdenCreada;
import co.com.sofka.domain.transporte.event.OrdenEntregada;
import co.com.sofka.domain.transporte.event.TransporteCreado;
import co.com.sofka.domain.transporte.valor.*;

import java.util.List;
import java.util.Map;

public class Transporte extends AggregateEvent<TransporteId> {
    protected Map<String, Orden> ordenes;
    protected List<Paquete> paquetes;
    protected List<Ruta> rutas;
    protected ConductorId conductorId;

    public Transporte(TransporteId transporteId, ConductorId conductorId) {
        super(transporteId);
        appendChange(new TransporteCreado(conductorId)).apply();
        subscribe(new TransporteEventChange(this));
    }

    private Transporte(TransporteId transporteId){
        super(transporteId);
        subscribe(new TransporteEventChange(this));
    }


    public static Transporte from(TransporteId transporteId, List<DomainEvent> events){
        var transporte = new Transporte(transporteId);
        events.forEach(transporte::applyEvent);
        return transporte;
    }

    public void crearOrden(OrdenId ordenId, Remitente remitente, Destinatario destinatario){
        appendChange(new OrdenCreada(ordenId, remitente, destinatario)).apply();
    }

    public void entregarOrden(OrdenId ordenId){
        appendChange(new OrdenEntregada(ordenId)).apply();
    }


    public void cambiarDestinatorioParaLaOrden(OrdenId ordenId, Destinatario destinatario){
        appendChange(new DestinatorioCambiado(ordenId, destinatario)).apply();
    }

    public Map<String, Orden> ordenes() {
        return ordenes;
    }

    public ConductorId conductorId() {
        return conductorId;
    }
}
