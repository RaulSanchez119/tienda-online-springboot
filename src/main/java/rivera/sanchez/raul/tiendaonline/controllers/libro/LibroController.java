package rivera.sanchez.raul.tiendaonline.controllers.libro;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rivera.sanchez.raul.tiendaonline.models.DiscoModel;
import rivera.sanchez.raul.tiendaonline.models.LibroModel;
import rivera.sanchez.raul.tiendaonline.services.LibroService;
import rivera.sanchez.raul.tiendaonline.services.Validador;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(value="/libro")
public class LibroController {
    @Autowired
    private LibroService libroService;

    @GetMapping()
    public ArrayList<LibroModel> obtenerLibros(){
        return libroService.obtenerLibros();
    }

    @GetMapping("{parametroBusqueda}")
    public Object obtenerLibroPorId(@PathVariable("parametroBusqueda") String parametroBusqueda){
        try{
            Long id = Long.parseLong(parametroBusqueda);
            return libroService.obtenerLibroPorId(id);
        } catch (NumberFormatException e){
            return libroService.obtenerLibroPorTitulo(parametroBusqueda);
        }
    }

    @GetMapping("/query")
    public ArrayList<LibroModel> obtenerLibrosPorTituloAutorIsbnEditorial(@RequestParam(required = false) String titulo,
                                                                          @RequestParam(required = false) String autor,
                                                                          @RequestParam(required = false) String editorial,
                                                                          @RequestParam(required = false) String isbn)
    {
        if(titulo != null) {
            return libroService.obtenerLibroPorTitulo(titulo);
        } else if(autor != null) {
            return libroService.obtenerLibroPorAutor(autor);
        } else if(editorial != null) {
            return libroService.obtenerLibroPorEditorial(editorial);
        } else if(isbn != null) {
            return libroService.obtenerLibroPorIsbn(isbn);
        } else {
            return null;
        }
    }

    @PostMapping()
    public String agregarLibro(@RequestBody LibroModel libro){

        String mensajeValidacion = Validador.validarCamposLibros(libro.getTitulo(), libro.getAutor(), libro.getIsbn(), libro.getPrecio(), libro.getEditorial());

        if(mensajeValidacion != null) {
            return mensajeValidacion;
        }

        if (libro.getId() == 0) {
            LibroModel libroAgregado = libroService.agregarOmodificarLibro(libro);

            if (libroAgregado != null) {
                return "Libro agregado exitosamente con id " + libroAgregado.getId();
            } else {
                return "El libro con el título " + libro.getTitulo() + " ya existe";
            }
        } else {
            LibroModel libroModificado = libroService.agregarOmodificarLibro(libro);

            if (libroModificado != null) {
                return "Libro modificado exitosamente con id " + libroModificado.getId();
            } else {
                return "El libro con el título " + libro.getTitulo() + " ya existe";
            }
        }
    }

    @DeleteMapping("{id}")
    public String  eliminarLibro(@PathVariable("id") Long id){
        boolean eliminado = libroService.eliminarLibro(id);
        if(eliminado) {
            return "Libro eliminado";
        } else {
            return "Libro no eliminado";
        }
    }
}