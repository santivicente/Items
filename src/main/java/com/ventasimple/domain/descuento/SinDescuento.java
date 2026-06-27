package com.ventasimple.domain.descuento;

/** Estrategia concreta: no aplica ningún descuento. */
public class SinDescuento implements EstrategiaDescuento {
    @Override
    public double aplicar(double subtotal) {
        return subtotal;
    }

    @Override
    public String getNombre() {
        return "Sin descuento";
    }
}
