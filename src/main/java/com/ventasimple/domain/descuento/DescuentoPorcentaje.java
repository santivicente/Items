package com.ventasimple.domain.descuento;

/** Estrategia concreta: descuenta un porcentaje fijo del subtotal. */
public class DescuentoPorcentaje implements EstrategiaDescuento {
    private final double porcentaje;

    public DescuentoPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public double aplicar(double subtotal) {
        return subtotal - (subtotal * porcentaje / 100.0);
    }

    @Override
    public String getNombre() {
        return "Descuento " + (int) porcentaje + "%";
    }
}
