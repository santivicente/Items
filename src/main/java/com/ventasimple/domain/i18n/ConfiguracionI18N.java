package com.ventasimple.domain.i18n;

import java.util.ArrayList;
import java.util.List;

/**
 * Sujeto concreto (ConcreteObservable). Guarda el formato de moneda y fecha;
 * al cambiar su estado notifica a todos los observadores registrados.
 */
public class ConfiguracionI18N implements IObservable {
    private String formatoMoneda = "USD";
    private String formatoFecha = "dd/MM/yyyy";
    private final List<IObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    @Override
    public void notificar() {
        for (IObserver o : observers) {
            o.actualizar();
        }
    }

    public void setConfiguracion(String moneda, String fecha) {
        this.formatoMoneda = moneda;
        this.formatoFecha = fecha;
        notificar();
    }

    public String getFormatoMoneda() {
        return formatoMoneda;
    }

    public String getFormatoFecha() {
        return formatoFecha;
    }
}
