package com.ventasimple.domain;

import com.ventasimple.domain.composite.ItemSimple;
import com.ventasimple.domain.composite.OrdenVenta;
import com.ventasimple.domain.descuento.DescuentoPorcentaje;
import com.ventasimple.domain.descuento.DescuentoVIP;
import com.ventasimple.domain.descuento.SinDescuento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StrategyTest {

    private OrdenVenta ordenDe(double monto) {
        OrdenVenta orden = new OrdenVenta("OV-1");
        orden.add(new ItemSimple("Producto", monto));
        return orden;
    }

    @Test
    void sinDescuentoDevuelveElSubtotal() {
        OrdenVenta orden = ordenDe(1000);
        orden.setDescuento(new SinDescuento());
        assertEquals(1000.0, orden.calcularTotalFinal(), 0.001);
    }

    @Test
    void descuentoPorcentajeAplica() {
        OrdenVenta orden = ordenDe(1000);
        orden.setDescuento(new DescuentoPorcentaje(10));
        assertEquals(900.0, orden.calcularTotalFinal(), 0.001);
    }

    @Test
    void descuentoVipAplica15PorCiento() {
        OrdenVenta orden = ordenDe(1000);
        orden.setDescuento(new DescuentoVIP());
        assertEquals(850.0, orden.calcularTotalFinal(), 0.001);
    }
}
