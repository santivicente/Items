package com.ventasimple.domain.i18n;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Observador concreto: formatea montos según el formato de moneda configurado.
 * Se registra solo al construirse y, en actualizar(), consulta el nuevo formato
 * al Subject (modelo pull).
 */
public class FormatoMoneda implements IObserver {
    private final ConfiguracionI18N config;
    private String formatoActual;

    public FormatoMoneda(ConfiguracionI18N config) {
        this.config = config;
        config.addObserver(this);
        actualizar();
    }

    @Override
    public void actualizar() {
        this.formatoActual = config.getFormatoMoneda();
    }

    public String mostrarMonto(double monto) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "AR"));
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        String simbolo = "USD".equals(formatoActual) ? "US$" : "$";
        return simbolo + " " + df.format(monto);
    }

    public String getFormatoActual() {
        return formatoActual;
    }
}
