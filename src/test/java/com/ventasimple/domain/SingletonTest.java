package com.ventasimple.domain;

import com.ventasimple.domain.composite.ItemSimple;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SingletonTest {

    @Test
    void getInstanciaDevuelveSiempreElMismoObjeto() {
        PuntoDeVenta a = PuntoDeVenta.getInstancia();
        PuntoDeVenta b = PuntoDeVenta.getInstancia();
        assertSame(a, b);
    }

    @Test
    void cerrarVentaRegistraEnElHistorialYAbreOrdenNueva() {
        PuntoDeVenta pdv = PuntoDeVenta.getInstancia();
        pdv.getOrdenActual().add(new ItemSimple("Producto", 500));
        int ventasAntes = pdv.getVentas().size();
        String numeroAntes = pdv.getOrdenActual().getNumero();

        pdv.cerrarVenta();

        assertEquals(ventasAntes + 1, pdv.getVentas().size());
        assertNotEquals(numeroAntes, pdv.getOrdenActual().getNumero());
    }
}
