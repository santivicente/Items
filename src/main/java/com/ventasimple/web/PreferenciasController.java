package com.ventasimple.web;

import com.ventasimple.domain.PuntoDeVenta;
import com.ventasimple.domain.i18n.ConfiguracionI18N;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class PreferenciasController {

    @GetMapping("/preferencias")
    public String ver(Model model) {
        PuntoDeVenta pdv = PuntoDeVenta.getInstancia();
        ConfiguracionI18N config = pdv.getConfiguracion();
        model.addAttribute("moneda", config.getFormatoMoneda());
        model.addAttribute("fecha", config.getFormatoFecha());
        model.addAttribute("previewMonto", pdv.getFormatoMoneda().mostrarMonto(2340));
        model.addAttribute("previewFecha", pdv.getFormatoFecha().mostrarFecha(LocalDate.now()));
        return "preferencias";
    }

    @PostMapping("/preferencias")
    public String guardar(@RequestParam String moneda, @RequestParam String fecha) {
        PuntoDeVenta.getInstancia().getConfiguracion().setConfiguracion(moneda, fecha);
        return "redirect:/preferencias";
    }
}
