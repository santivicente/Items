package com.ventasimple.domain.composite;

/**
 * Componente del patrón Composite. Tanto los ítems simples (hojas) como los
 * compuestos (kits) y la propia orden implementan esta interfaz, de modo que
 * el cliente los trata de manera uniforme.
 */
public interface ItemVenta {
    double calcularTotal();
    String getDescripcion();
}
