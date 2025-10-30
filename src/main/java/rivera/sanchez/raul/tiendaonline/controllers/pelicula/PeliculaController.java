package rivera.sanchez.raul.tiendaonline.controllers.pelicula;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rivera.sanchez.raul.tiendaonline.enums.Formato;
import rivera.sanchez.raul.tiendaonline.models.DiscoModel;
import rivera.sanchez.raul.tiendaonline.models.PeliculaModel;
import rivera.sanchez.raul.tiendaonline.services.PeliculaService;
import rivera.sanchez.raul.tiendaonline.services.Validador;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/pelicula")
public class PeliculaController {
    @Autowired
    private PeliculaService peliculaService;

    @GetMapping()
    public ArrayList<PeliculaModel> obtenerPeliculas() {
        return peliculaService.obtenerPeliculas();
    }

    @RequestMapping("{parametroBusqueda}")
    public Object obtenerPelicula(@PathVariable("parametroBusqueda") String parametroBusqueda) {
        try{
            Long id = Long.parseLong(parametroBusqueda);
            return peliculaService.obtenerPeliculaPorId(id);
        } catch (NumberFormatException e) {
            return peliculaService.obtenerPeliculasPorTitulo(parametroBusqueda);
        }
    }

    @RequestMapping("/query")
    public ArrayList<PeliculaModel> obtenerPeliculasPorTituloFormatoDirector(@RequestParam(required = false) String titulo,
                                                                             @RequestParam(required = false) String director,
                                                                             @RequestParam(required = false) Formato formato,
                                                                             @RequestParam(required = false) Integer anyo) {
        if(titulo != null) {
            return peliculaService.obtenerPeliculasPorTitulo(titulo);
        } else if(director != null) {
            return peliculaService.obtenerPeliculasPorDirector(director);
        } else if(formato != null) {
            return peliculaService.obtenerPeliculasPorFormato(formato);
        } else if(anyo != null) {
            return peliculaService.obtenerPeliculasPorAnyo(anyo);
        } else {
            return null;
        }
    }
    @PostMapping()
    public String agregarPelicula(@RequestBody PeliculaModel pelicula) {
        String mensajeValidacion = Validador.validarCamposPeliculas(pelicula.getTitulo(), pelicula.getFormato(), pelicula.getAnyo(), pelicula.getPrecio(), pelicula.getDirector());

        if(mensajeValidacion != null) {
            return mensajeValidacion;
        }

        if (pelicula.getId() == 0) {
            PeliculaModel peliculaAgregada = peliculaService.agregarOmodificarPelicula(pelicula);

            if (peliculaAgregada != null) {
                return "Película agregada exitosamente con id " + peliculaAgregada.getId();
            } else {
                return "La película con el título " + pelicula.getTitulo() + " ya existe";
            }
        } else {
            PeliculaModel peliculaModificada = peliculaService.agregarOmodificarPelicula(pelicula);

            if (peliculaModificada != null) {
                return "Pelicula modificada exitosamente con id " + peliculaModificada.getId();
            } else {
                return "La película con el título " + pelicula.getTitulo() + " ya existe";
            }
        }
    }

    @DeleteMapping("{id}")
    public String eliminarPelicula(@PathVariable Long id) {
        boolean eliminado = peliculaService.eliminarPelicula(id);
        if(eliminado) {
            return "Pelicula eliminada";
        } else {
            return "Pelicula no eliminada";
        }
    }
}