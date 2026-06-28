package com.ventasimple.domain;

import com.ventasimple.domain.composite.OrdenVenta;
import com.ventasimple.domain.i18n.ConfiguracionI18N;
import com.ventasimple.domain.i18n.FormatoFecha;
import com.ventasimple.domain.i18n.FormatoMoneda;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private final List<Venta> ventas = new ArrayList<>();
    private OrdenVenta ordenActual;
    private int contadorOrdenes = 1;

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
        contadorOrdenes++;
        this.ordenActual = new OrdenVenta(String.format("OV-%03d", contadorOrdenes));
        return ordenActual;
    }

    /**
     * Cierra la orden actual: guarda una foto de la venta en el historial y
     * arranca una orden nueva. El registro de ventas vive en esta única caja (Singleton).
     */
    public Venta cerrarVenta() {
        OrdenVenta o = ordenActual;
        LocalDate hoy = LocalDate.now();
        double totalFinal = o.calcularTotalFinal();
        Venta venta = new Venta(
                o.getNumero(),
                hoy,
                formatoFecha.mostrarFecha(hoy),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                o.getDescuento().getNombre(),
                totalFinal,
                formatoMoneda.mostrarMonto(totalFinal),
                o.getItems().size()
        );
        ventas.add(venta);
        nuevaOrden();
        return venta;
    }

    public List<Venta> getVentas() {
        return ventas;
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
