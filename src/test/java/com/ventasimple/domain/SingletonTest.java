package com.ventasimple.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class SingletonTest {

    @Test
    void getInstanciaDevuelveSiempreElMismoObjeto() {
        PuntoDeVenta a = PuntoDeVenta.getInstancia();
        PuntoDeVenta b = PuntoDeVenta.getInstancia();
        assertSame(a, b);
    }
}
