package com.ventasimple.domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Foto (snapshot) de una venta ya cerrada. Guarda los valores —incluido el total
 * y el detalle de ítems YA FORMATEADOS con la moneda/fecha vigentes en el momento
 * del cierre— para que el historial muestre cada venta tal cual se emitió, aunque
 * después se cambie la configuración (i18n).
 */
public class Venta {
    private final String numero;
    private final LocalDate fecha;
    private final String fechaFormateada;
    private final String hora;
    private final String descuento;
    private final double total;
    private final String totalFormateado;
    private final int cantidadItems;
    private final List<String> detalle;

    public Venta(String numero, LocalDate fecha, String fechaFormateada, String hora,
                 String descuento, double total, String totalFormateado,
                 int cantidadItems, List<String> detalle) {
        this.numero = numero;
        this.fecha = fecha;
        this.fechaFormateada = fechaFormateada;
        this.hora = hora;
        this.descuento = descuento;
        this.total = total;
        this.totalFormateado = totalFormateado;
        this.cantidadItems = cantidadItems;
        this.detalle = detalle;
    }

    public String getNumero() { return numero; }
    public LocalDate getFecha() { return fecha; }
    public String getFechaFormateada() { return fechaFormateada; }
    public String getHora() { return hora; }
    public String getDescuento() { return descuento; }
    public double getTotal() { return total; }
    public String getTotalFormateado() { return totalFormateado; }
    public int getCantidadItems() { return cantidadItems; }
    public List<String> getDetalle() { return detalle; }
}
