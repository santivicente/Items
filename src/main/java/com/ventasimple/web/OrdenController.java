package com.ventasimple.web;

import com.ventasimple.domain.Catalogo;
import com.ventasimple.domain.PuntoDeVenta;
import com.ventasimple.domain.composite.ItemCompuesto;
import com.ventasimple.domain.composite.ItemSimple;
import com.ventasimple.domain.composite.OrdenVenta;
import com.ventasimple.domain.descuento.DescuentoPorcentaje;
import com.ventasimple.domain.descuento.DescuentoVIP;
import com.ventasimple.domain.descuento.SinDescuento;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrdenController {

    private PuntoDeVenta pdv() {
        return PuntoDeVenta.getInstancia();
    }

    @GetMapping("/")
    public String armarOrden(Model model) {
        cargarComunes(model);
        model.addAttribute("catalogo", Catalogo.getProductos());
        return "armar-orden";
    }

    @PostMapping("/items/simple")
    public String agregarSimple(@RequestParam int productoId, RedirectAttributes ra) {
        Catalogo.Producto p = Catalogo.porId(productoId);
        pdv().getOrdenActual().add(new ItemSimple(p.getNombre(), p.getPrecio()));
        ra.addFlashAttribute("mensaje", "✓ Se agregó \"" + p.getNombre() + "\" a la orden.");
        ra.addFlashAttribute("tipoMensaje", "ok");
        return "redirect:/";
    }

    @PostMapping("/items/kit")
    public String agregarKit(@RequestParam String kitNombre,
                             @RequestParam(required = false) List<Integer> productoIds,
                             RedirectAttributes ra) {
        if (productoIds != null && !productoIds.isEmpty()) {
            ItemCompuesto kit = new ItemCompuesto(kitNombre);
            for (Integer id : productoIds) {
                Catalogo.Producto p = Catalogo.porId(id);
                kit.add(new ItemSimple(p.getNombre(), p.getPrecio()));
            }
            pdv().getOrdenActual().add(kit);
            ra.addFlashAttribute("mensaje",
                    "✓ Se agregó el kit \"" + kitNombre + "\" con " + productoIds.size() + " productos.");
            ra.addFlashAttribute("tipoMensaje", "ok");
        } else {
            ra.addFlashAttribute("mensaje", "⚠ Elegí al menos un producto para armar el kit.");
            ra.addFlashAttribute("tipoMensaje", "warn");
        }
        return "redirect:/";
    }

    @PostMapping("/nueva")
    public String nuevaOrden() {
        pdv().nuevaOrden();
        return "redirect:/";
    }

    @GetMapping("/total")
    public String total(Model model) {
        cargarComunes(model);
        return "ticket";
    }

    @PostMapping("/total")
    public String aplicarDescuento(@RequestParam String tipo, Model model) {
        OrdenVenta orden = pdv().getOrdenActual();
        switch (tipo) {
            case "porcentaje" -> orden.setDescuento(new DescuentoPorcentaje(10));
            case "vip" -> orden.setDescuento(new DescuentoVIP());
            default -> orden.setDescuento(new SinDescuento());
        }
        cargarComunes(model);
        return "ticket";
    }

    @PostMapping("/cerrar")
    public String cerrarVenta() {
        OrdenVenta orden = pdv().getOrdenActual();
        if (!orden.getItems().isEmpty()) {
            pdv().cerrarVenta();
        }
        return "redirect:/ventas";
    }

    @GetMapping("/ventas")
    public String ventas(Model model) {
        // Cada venta ya guarda su total y fecha formateados al momento del cierre,
        // por eso no se vuelve a formatear acá (así no cambia el historial al cambiar la config).
        model.addAttribute("ventas", pdv().getVentas());
        return "ventas";
    }

    private void cargarComunes(Model model) {
        PuntoDeVenta pdv = pdv();
        OrdenVenta orden = pdv.getOrdenActual();
        model.addAttribute("orden", orden);
        model.addAttribute("items", orden.getItems());
        model.addAttribute("fmt", pdv.getFormatoMoneda());
        model.addAttribute("subtotal", orden.calcularTotal());
        model.addAttribute("totalFinal", orden.calcularTotalFinal());
        model.addAttribute("descuentoNombre", orden.getDescuento().getNombre());
        model.addAttribute("tipoDescuento", tipoDescuentoActual(orden));
        model.addAttribute("fechaVenta", pdv.getFormatoFecha().mostrarFecha(java.time.LocalDate.now()));
    }

    /** Mapea la estrategia actual a la clave del desplegable, para marcar la opción elegida. */
    private String tipoDescuentoActual(OrdenVenta orden) {
        if (orden.getDescuento() instanceof DescuentoPorcentaje) {
            return "porcentaje";
        }
        if (orden.getDescuento() instanceof DescuentoVIP) {
            return "vip";
        }
        return "sin";
    }
}
