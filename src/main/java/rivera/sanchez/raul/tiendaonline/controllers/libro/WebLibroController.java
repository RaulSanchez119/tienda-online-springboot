package rivera.sanchez.raul.tiendaonline.controllers.libro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rivera.sanchez.raul.tiendaonline.models.LibroModel;
import rivera.sanchez.raul.tiendaonline.services.LibroService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/libros")
public class WebLibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {
        ArrayList<LibroModel> listado = libroService.obtenerLibros();
        model.addAttribute("libros", listado);
        return "libros/catalogoLibros";
    }

    @RequestMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable("id") Long id) {
        libroService.eliminarLibro(id);
        return "redirect:/libros/catalogo";
    }

    @GetMapping("/form/modificar/{id}")
    public String mostrarFormularioModificacion(@PathVariable("id") Long id, Model model) {
        Optional<LibroModel> libro = libroService.obtenerLibroPorId(id);
        if (libro.isPresent()) {
            model.addAttribute("libro", libro.get());
            return "libros/formModificarLibro";
        } else {
            model.addAttribute("error", "Libro no encontrado.");
            return "error";
        }
    }

    @PostMapping("/modificar")
    public String modificarLibro(@ModelAttribute LibroModel libro, Model model) {
        LibroModel libroModificado = libroService.agregarOmodificarLibro(libro);

        String mensaje = (libroModificado == null)
                ? "El libro con el título '" + libro.getTitulo() + "' ya existe."
                : "Libro modificado con éxito. ID: " + libro.getId();

        String tipoMensaje = (libroModificado == null) ? "error" : "exito";

        model.addAttribute(tipoMensaje, mensaje);
        model.addAttribute("libro", libro);

        return "libros/formModificarLibro";
    }

    @GetMapping("/form/agregar")
    public String mostrarFormularioAgregar(Model model) {
        model.addAttribute("libro", new LibroModel());
        return "libros/formAgregarLibro";
    }

    @PostMapping("/agregar")
    public String agregarDisco(@ModelAttribute LibroModel libro, Model model) {
        LibroModel libroAgregado = libroService.agregarOmodificarLibro(libro);

        String mensaje = (libroAgregado == null)
                ? "El libro con el título '" + libro.getTitulo() + "' ya existe."
                : "Libro creado con éxito. ID: " + libroAgregado.getId();

        String tipoMensaje = (libroAgregado == null) ? "error" : "exito";

        model.addAttribute(tipoMensaje, mensaje);
        return "libros/formAgregarLibro";
    }

    @GetMapping("/buscar")
    public String mostrarPaginaBusqueda(Model model) {
        ArrayList<LibroModel> listado = libroService.obtenerLibros();
        model.addAttribute("libros", listado);
        return "libros/buscarLibros";
    }

    @GetMapping("/buscar/libro")
    public String buscarLibro(@RequestParam(required = false) Long idLibro,
                              @RequestParam(required = false) String titulo,
                              @RequestParam(required = false) String autor,
                              @RequestParam(required = false) String isbn,
                              @RequestParam(required = false) String editorial,
                              Model model) {

        List<LibroModel> listado = libroService.filtrarLibros(idLibro, titulo, autor, isbn, editorial);
        model.addAttribute("libros", listado);

        return "/libros/buscarLibros";
    }


}
