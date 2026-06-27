package com.ventasimple.domain.descuento;

/**
 * Strategy: familia de algoritmos de descuento intercambiables. Recibe el
 * subtotal de la orden y devuelve el total ya con el descuento aplicado.
 */
public interface EstrategiaDescuento {
    double aplicar(double subtotal);
    String getNombre();
}
