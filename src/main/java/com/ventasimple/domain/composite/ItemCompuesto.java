package com.ventasimple.domain.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite interno: un kit que agrupa varios ItemVenta (que a su vez pueden
 * ser simples u otros compuestos). Delega el cálculo recursivamente en sus partes.
 */
public class ItemCompuesto implements ItemVenta {
    private final String descripcion;
    private final List<ItemVenta> partes = new ArrayList<>();

    public ItemCompuesto(String descripcion) {
        this.descripcion = descripcion;
    }

    public void add(ItemVenta item) {
        partes.add(item);
    }

    public void remove(ItemVenta item) {
        partes.remove(item);
    }

    public List<ItemVenta> getPartes() {
        return partes;
    }

    @Override
    public double calcularTotal() {
        return partes.stream().mapToDouble(ItemVenta::calcularTotal).sum();
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
