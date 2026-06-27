package com.ventasimple.web;

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

@Controller
public class OrdenController {

    private PuntoDeVenta pdv() {
        return PuntoDeVenta.getInstancia();
    }

    @GetMapping("/")
    public String armarOrden(Model model) {
        cargarComunes(model);
        return "armar-orden";
    }

    @PostMapping("/items/simple")
    public String agregarSimple(@RequestParam String nombre, @RequestParam double precio) {
        pdv().getOrdenActual().add(new ItemSimple(nombre, precio));
        return "redirect:/";
    }

    @PostMapping("/items/kit")
    public String agregarKit(@RequestParam String kitNombre,
                             @RequestParam String nombre1, @RequestParam double precio1,
                             @RequestParam String nombre2, @RequestParam double precio2) {
        ItemCompuesto kit = new ItemCompuesto(kitNombre);
        kit.add(new ItemSimple(nombre1, precio1));
        kit.add(new ItemSimple(nombre2, precio2));
        pdv().getOrdenActual().add(kit);
        return "redirect:/";
    }

    @PostMapping("/nueva")
    public String nuevaOrden() {
        pdv().nuevaOrden();
        return "redirect:/";
    }

    @GetMapping("/resumen")
    public String resumen(Model model) {
        cargarComunes(model);
        return "resumen";
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

    private void cargarComunes(Model model) {
        PuntoDeVenta pdv = pdv();
        OrdenVenta orden = pdv.getOrdenActual();
        model.addAttribute("orden", orden);
        model.addAttribute("items", orden.getItems());
        model.addAttribute("fmt", pdv.getFormatoMoneda());
        model.addAttribute("subtotal", orden.calcularTotal());
        model.addAttribute("totalFinal", orden.calcularTotalFinal());
        model.addAttribute("descuentoNombre", orden.getDescuento().getNombre());
    }
}
