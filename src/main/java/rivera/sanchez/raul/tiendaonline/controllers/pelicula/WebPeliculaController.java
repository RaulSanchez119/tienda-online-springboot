package rivera.sanchez.raul.tiendaonline.controllers.pelicula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rivera.sanchez.raul.tiendaonline.enums.Formato;
import rivera.sanchez.raul.tiendaonline.models.PeliculaModel;
import rivera.sanchez.raul.tiendaonline.services.PeliculaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/peliculas")
public class WebPeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {
        ArrayList<PeliculaModel> listado = peliculaService.obtenerPeliculas();
        model.addAttribute("peliculas", listado);
        return "/peliculas/catalogoPeliculas";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminarPeliculas(@PathVariable("id") Long id) {
        peliculaService.eliminarPelicula(id);
        return "redirect:/peliculas/catalogo";
    }

    @GetMapping("/form/modificar/{id}")
    public String mostrarFormularioModificacion(@PathVariable("id") Long id, Model model) {
        Optional<PeliculaModel> pelicula = peliculaService.obtenerPeliculaPorId(id);
        if (pelicula.isPresent()) {
            model.addAttribute("pelicula", pelicula.get());
            return "peliculas/formModificarPelicula";
        } else {
            model.addAttribute("error", "Película no encontrado.");
            return "error/404";
        }
    }

    @PostMapping("/modificar")
    public String modificarPelicula(@ModelAttribute PeliculaModel pelicula, Model model) {
        PeliculaModel peliculaModificada = peliculaService.agregarOmodificarPelicula(pelicula);

        String mensaje = (peliculaModificada == null)
                ? "La película con el título '" + pelicula.getTitulo() + "' ya existe."
                : "Película modificada con éxito. ID: " + peliculaModificada.getId();

        String tipoMensaje = (peliculaModificada == null) ? "error" : "exito";

        model.addAttribute(tipoMensaje, mensaje);
        model.addAttribute("pelicula", pelicula);

        return "peliculas/formModificarPelicula";
    }

    @GetMapping("/form/agregar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("pelicula", new PeliculaModel());
        return "peliculas/formAgregarPelicula";
    }

    @PostMapping("/agregar")
    public String agregarPelicula(@ModelAttribute PeliculaModel pelicula, Model model) {
        PeliculaModel peliculaAgregada = peliculaService.agregarOmodificarPelicula(pelicula);

        String mensaje = (peliculaAgregada == null)
                ? "La película con el título '" + pelicula.getTitulo() + "' ya existe."
                : "Película creada con éxito. ID: " + peliculaAgregada.getId();

        String tipoMensaje = (peliculaAgregada == null) ? "error" : "exito";

        model.addAttribute(tipoMensaje, mensaje);
        return "peliculas/formAgregarPelicula";
    }

    @GetMapping("/buscar")
    public String mostrarPaginaBusqueda(Model model) {
        ArrayList<PeliculaModel> listado = peliculaService.obtenerPeliculas();
        model.addAttribute("peliculas", listado);
        return "/peliculas/buscarPeliculas";
    }

    @GetMapping("/buscar/pelicula")
    public String buscarDisco(@RequestParam(required = false) Long idPelicula,
                              @RequestParam(required = false) String titulo,
                              @RequestParam(required = false) Formato formato,
                              @RequestParam(required = false) Integer anyo,
                              @RequestParam(required = false) String director,
                              Model model) {

        List<PeliculaModel> listado = peliculaService.filtrarPeliculas(idPelicula, titulo, formato, anyo, director);
        model.addAttribute("peliculas", listado);
        model.addAttribute("formato", formato);

        return "/peliculas/buscarPeliculas";
    }
}
