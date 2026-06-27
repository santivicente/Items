package com.ventasimple.domain.composite;

import com.ventasimple.domain.descuento.EstrategiaDescuento;
import com.ventasimple.domain.descuento.SinDescuento;

import java.util.ArrayList;
import java.util.List;

/**
 * Raíz del árbol Composite. Suma el total de todos sus ítems (simples y kits).
 * Además es el contexto del patrón Strategy: delega el descuento del total final
 * en una EstrategiaDescuento, sin mezclar esa lógica con el cálculo del Composite.
 */
public class OrdenVenta implements ItemVenta {
    private final String numero;
    private final List<ItemVenta> items = new ArrayList<>();
    private EstrategiaDescuento descuento = new SinDescuento();

    public OrdenVenta(String numero) {
        this.numero = numero;
    }

    public void add(ItemVenta item) {
        items.add(item);
    }

    public List<ItemVenta> getItems() {
        return items;
    }

    public String getNumero() {
        return numero;
    }

    public void setDescuento(EstrategiaDescuento descuento) {
        this.descuento = descuento;
    }

    public EstrategiaDescuento getDescuento() {
        return descuento;
    }

    /** Total del Composite: suma recursiva del árbol, sin descuento. */
    @Override
    public double calcularTotal() {
        return items.stream().mapToDouble(ItemVenta::calcularTotal).sum();
    }

    /** Total final: aplica la estrategia de descuento sobre el subtotal del Composite. */
    public double calcularTotalFinal() {
        return descuento.aplicar(calcularTotal());
    }

    @Override
    public String getDescripcion() {
        return "Orden #" + numero;
    }
}
