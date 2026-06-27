package com.ventasimple.domain.i18n;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Observador concreto: formatea fechas según el formato configurado.
 */
public class FormatoFecha implements IObserver {
    private final ConfiguracionI18N config;
    private String formatoActual;

    public FormatoFecha(ConfiguracionI18N config) {
        this.config = config;
        config.addObserver(this);
        actualizar();
    }

    @Override
    public void actualizar() {
        this.formatoActual = config.getFormatoFecha();
    }

    public String mostrarFecha(LocalDate fecha) {
        return fecha.format(DateTimeFormatter.ofPattern(formatoActual));
    }

    public String getFormatoActual() {
        return formatoActual;
    }
}
