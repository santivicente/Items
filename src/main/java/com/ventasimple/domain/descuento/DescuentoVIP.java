package com.ventasimple.domain.descuento;

/** Estrategia concreta: descuento fijo del 15% para clientes VIP. */
public class DescuentoVIP implements EstrategiaDescuento {
    private static final double PORCENTAJE_VIP = 15.0;

    @Override
    public double aplicar(double subtotal) {
        return subtotal - (subtotal * PORCENTAJE_VIP / 100.0);
    }

    @Override
    public String getNombre() {
        return "Cliente VIP (15%)";
    }
}
