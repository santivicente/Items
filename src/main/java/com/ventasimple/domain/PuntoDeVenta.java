package com.ventasimple.domain;

import com.ventasimple.domain.composite.OrdenVenta;
import com.ventasimple.domain.i18n.ConfiguracionI18N;
import com.ventasimple.domain.i18n.FormatoFecha;
import com.ventasimple.domain.i18n.FormatoMoneda;

/**
 * Singleton: única instancia del punto de venta. Mantiene la orden actual y la
 * configuración de internacionalización (con sus observadores de formato),
 * garantizando un estado consistente en toda la aplicación.
 */
public class PuntoDeVenta {
    private static volatile PuntoDeVenta instancia;

    private final String nombre;
    private final ConfiguracionI18N config;
    private final FormatoMoneda formatoMoneda;
    private final FormatoFecha formatoFecha;
    private OrdenVenta ordenActual;

    private PuntoDeVenta() {
        this.nombre = "VentaSimple";
        this.config = new ConfiguracionI18N();
        this.formatoMoneda = new FormatoMoneda(config);
        this.formatoFecha = new FormatoFecha(config);
        this.ordenActual = new OrdenVenta("OV-001");
    }

    public static PuntoDeVenta getInstancia() {
        if (instancia == null) {
            synchronized (PuntoDeVenta.class) {
                if (instancia == null) {
                    instancia = new PuntoDeVenta();
                }
            }
        }
        return instancia;
    }

    public OrdenVenta nuevaOrden() {
        this.ordenActual = new OrdenVenta("OV-" + System.currentTimeMillis());
        return ordenActual;
    }

    public String getNombre() {
        return nombre;
    }

    public OrdenVenta getOrdenActual() {
        return ordenActual;
    }

    public ConfiguracionI18N getConfiguracion() {
        return config;
    }

    public FormatoMoneda getFormatoMoneda() {
        return formatoMoneda;
    }

    public FormatoFecha getFormatoFecha() {
        return formatoFecha;
    }
}
