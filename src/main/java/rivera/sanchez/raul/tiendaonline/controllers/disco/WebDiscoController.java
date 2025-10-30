package rivera.sanchez.raul.tiendaonline.controllers.disco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rivera.sanchez.raul.tiendaonline.models.DiscoModel;
import rivera.sanchez.raul.tiendaonline.services.DiscoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/discos")
public class WebDiscoController {

    @Autowired
    private DiscoService discoService;

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {
        ArrayList<DiscoModel> listado = discoService.obtenerDiscos();
        model.addAttribute("discos", listado);
        return "/discos/catalogoDiscos";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminarDisco(@PathVariable("id") Long id) {
        discoService.eliminarDisco(id);
        return "redirect:/discos/catalogo";
    }

    @GetMapping("/form/modificar/{parametroBusqueda}")
    public String mostrarFormularioModificacion(@PathVariable("parametroBusqueda") String parametroBusqueda, Model model) {
        Optional<DiscoModel> disco = Optional.empty();

        try {
            Long id = Long.parseLong(parametroBusqueda);
            disco = discoService.obtenerDiscoPorId(id);
        } catch (NumberFormatException e) {
            ArrayList<DiscoModel> discos = discoService.obtenerDiscoPorTitulo(parametroBusqueda);
            if (!discos.isEmpty()) {
                disco = Optional.of(discos.getFirst());
            }
        }
        if (disco.isPresent()) {
            model.addAttribute("disco", disco.get());
            return "discos/formModificarDisco";
        } else {
            model.addAttribute("error", "Disco no encontrado.");
            return "error";
        }
    }

    @PostMapping("/modificar")
    public String modificarDisco(@ModelAttribute DiscoModel disco, Model model) {
        DiscoModel discoModificado = discoService.agregarOmodificarDisco(disco);

        String mensaje = (discoModificado == null)
                ? "El disco con el título '" + disco.getTitulo() + "' ya existe."
                : "Disco modificado con éxito. ID: " + discoModificado.getId();

        String tipoMensaje = (discoModificado == null) ? "error" : "exito";

        model.addAttribute(tipoMensaje, mensaje);
        model.addAttribute("disco", disco);

        return "discos/formModificarDisco";
    }

    @GetMapping("/form/agregar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("disco", new DiscoModel());
        return "discos/formAgregarDisco";
    }

    @PostMapping("/agregar")
    public String agregarDisco(@ModelAttribute DiscoModel disco, Model model) {
        DiscoModel discoAgregado = discoService.agregarOmodificarDisco(disco);

        String mensaje = (discoAgregado == null)
                ? "El disco con el título '" + disco.getTitulo() + "' ya existe."
                : "Disco creado con éxito. ID: " + discoAgregado.getId();

        String tipoMensaje = (discoAgregado == null) ? "error" : "exito";

        model.addAttribute(tipoMensaje, mensaje);
        return "discos/formAgregarDisco";
    }

    @GetMapping("/buscar")
    public String mostrarPaginaBusqueda(Model model) {
        ArrayList<DiscoModel> listado = discoService.obtenerDiscos();
        model.addAttribute("discos", listado);
        return "/discos/buscarDiscos";
    }

    @GetMapping("/buscar/disco")
    public String buscarDisco(@RequestParam(required = false) Long idDisco,
                              @RequestParam(required = false) String titulo,
                              @RequestParam(required = false) String autor,
                              @RequestParam(required = false) Integer anyo,
                              @RequestParam(required = false) Integer pistas,
                              Model model) {

        List<DiscoModel> listado = discoService.filtrarDiscos(idDisco, titulo, autor, anyo, pistas);
        model.addAttribute("discos", listado);

        return "/discos/buscarDiscos";
    }

}
