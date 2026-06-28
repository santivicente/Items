package com.ventasimple.domain;

import java.time.LocalDate;

/**
 * Foto (snapshot) de una venta ya cerrada. Guarda los valores en el momento del
 * cierre para que queden fijos en el historial aunque después se arme otra orden.
 */
public class Venta {
    private final String numero;
    private final LocalDate fecha;
    private final String hora;
    private final String descuento;
    private final double subtotal;
    private final double total;
    private final int cantidadItems;

    public Venta(String numero, LocalDate fecha, String hora, String descuento,
                 double subtotal, double total, int cantidadItems) {
        this.numero = numero;
        this.fecha = fecha;
        this.hora = hora;
        this.descuento = descuento;
        this.subtotal = subtotal;
        this.total = total;
        this.cantidadItems = cantidadItems;
    }

    public String getNumero() { return numero; }
    public LocalDate getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getDescuento() { return descuento; }
    public double getSubtotal() { return subtotal; }
    public double getTotal() { return total; }
    public int getCantidadItems() { return cantidadItems; }
}
