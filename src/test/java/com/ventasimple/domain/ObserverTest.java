package com.ventasimple.domain;

import com.ventasimple.domain.i18n.ConfiguracionI18N;
import com.ventasimple.domain.i18n.FormatoFecha;
import com.ventasimple.domain.i18n.FormatoMoneda;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObserverTest {

    @Test
    void losObservadoresSeActualizanAlCambiarConfig() {
        ConfiguracionI18N config = new ConfiguracionI18N();
        FormatoMoneda moneda = new FormatoMoneda(config);
        FormatoFecha fecha = new FormatoFecha(config);

        config.setConfiguracion("USD", "yyyy-MM-dd");

        assertEquals("USD", moneda.getFormatoActual());
        assertEquals("yyyy-MM-dd", fecha.getFormatoActual());
    }

    @Test
    void formatoMonedaMuestraMontoConSeparadorDeMiles() {
        ConfiguracionI18N config = new ConfiguracionI18N();
        FormatoMoneda moneda = new FormatoMoneda(config);

        String salida = moneda.mostrarMonto(2600);

        assertTrue(salida.contains("2.600"), "Esperaba separador de miles, fue: " + salida);
    }

    @Test
    void enPesosConvierteSegunLaCotizacion() {
        ConfiguracionI18N config = new ConfiguracionI18N();
        FormatoMoneda moneda = new FormatoMoneda(config);

        config.setConfiguracion("ARS", "dd/MM/yyyy");
        String salida = moneda.mostrarMonto(1000); // 1000 USD * 1500 = 1.500.000

        assertTrue(salida.contains("1.500.000"), "Esperaba conversion a pesos, fue: " + salida);
        assertTrue(salida.startsWith("$"), "Esperaba simbolo de pesos, fue: " + salida);
    }
}
