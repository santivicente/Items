package com.ventasimple.domain;

import com.ventasimple.domain.composite.ItemCompuesto;
import com.ventasimple.domain.composite.ItemSimple;
import com.ventasimple.domain.composite.OrdenVenta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompositeTest {

    @Test
    void calculaTotalConItemsSimplesYKitAnidado() {
        OrdenVenta orden = new OrdenVenta("OV-1");
        orden.add(new ItemSimple("Notebook", 1500));
        orden.add(new ItemSimple("Monitor", 900));

        ItemCompuesto kit = new ItemCompuesto("Kit Accesorios");
        kit.add(new ItemSimple("Mouse", 80));
        kit.add(new ItemSimple("Teclado", 120));
        orden.add(kit);

        // 1500 + 900 + (80 + 120) = 2600
        assertEquals(2600.0, orden.calcularTotal(), 0.001);
    }
}
