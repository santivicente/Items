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

    /** Cotización: 1 dólar = 1500 pesos. Los montos base están en dólares (USD). */
    public static final double COTIZACION_USD_ARS = 1500.0;

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

    /**
     * Recibe un monto en dólares (moneda base) y lo muestra según el formato configurado.
     * En pesos (ARS) lo convierte multiplicando por la cotización.
     */
    public String mostrarMonto(double montoUsd) {
        double valor = montoUsd;
        String simbolo = "US$";
        if ("ARS".equals(formatoActual)) {
            valor = montoUsd * COTIZACION_USD_ARS;
            simbolo = "$";
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "AR"));
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return simbolo + " " + df.format(valor);
    }

    public String getFormatoActual() {
        return formatoActual;
    }
}
