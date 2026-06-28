package com.ventasimple.domain;

import java.time.LocalDate;

/**
 * Foto (snapshot) de una venta ya cerrada. Guarda los valores —incluido el total
 * y la fecha YA FORMATEADOS con la moneda/fecha vigentes en el momento del cierre—
 * para que el historial muestre cada venta en su moneda original, aunque después
 * se cambie la configuración (i18n).
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

    public Venta(String numero, LocalDate fecha, String fechaFormateada, String hora,
                 String descuento, double total, String totalFormateado, int cantidadItems) {
        this.numero = numero;
        this.fecha = fecha;
        this.fechaFormateada = fechaFormateada;
        this.hora = hora;
        this.descuento = descuento;
        this.total = total;
        this.totalFormateado = totalFormateado;
        this.cantidadItems = cantidadItems;
    }

    public String getNumero() { return numero; }
    public LocalDate getFecha() { return fecha; }
    public String getFechaFormateada() { return fechaFormateada; }
    public String getHora() { return hora; }
    public String getDescuento() { return descuento; }
    public double getTotal() { return total; }
    public String getTotalFormateado() { return totalFormateado; }
    public int getCantidadItems() { return cantidadItems; }
}
