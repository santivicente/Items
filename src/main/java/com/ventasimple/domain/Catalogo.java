package com.ventasimple.domain;

import java.util.List;

/**
 * Catálogo de productos predefinidos del comercio. Es dato de referencia: desde
 * acá el vendedor agrega productos a la orden (sueltos o dentro de un kit).
 */
public class Catalogo {

    /** Producto disponible para la venta (id estable para los formularios). */
    public static class Producto {
        private final int id;
        private final String nombre;
        private final double precio;

        public Producto(int id, String nombre, double precio) {
            this.id = id;
            this.nombre = nombre;
            this.precio = precio;
        }

        public int getId() { return id; }
        public String getNombre() { return nombre; }
        public double getPrecio() { return precio; }
    }

    private static final List<Producto> PRODUCTOS = List.of(
            new Producto(1, "Notebook", 1500),
            new Producto(2, "Monitor", 900),
            new Producto(3, "Mouse", 80),
            new Producto(4, "Teclado", 120),
            new Producto(5, "Auriculares", 500),
            new Producto(6, "Webcam", 350),
            new Producto(7, "Pendrive 64GB", 60),
            new Producto(8, "Cargador USB-C", 90)
    );

    public static List<Producto> getProductos() {
        return PRODUCTOS;
    }

    public static Producto porId(int id) {
        return PRODUCTOS.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Producto inexistente: " + id));
    }
}
