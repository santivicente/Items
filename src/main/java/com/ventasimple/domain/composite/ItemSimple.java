package com.ventasimple.domain.composite;

/**
 * Hoja (Leaf) del patrón Composite: un producto individual con nombre y precio.
 */
public class ItemSimple implements ItemVenta {
    private final String nombre;
    private final double precio;

    public ItemSimple(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public double calcularTotal() {
        return precio;
    }

    @Override
    public String getDescripcion() {
        return nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}
