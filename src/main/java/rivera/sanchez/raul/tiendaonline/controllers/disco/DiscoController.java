package rivera.sanchez.raul.tiendaonline.controllers.disco;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rivera.sanchez.raul.tiendaonline.models.DiscoModel;
import rivera.sanchez.raul.tiendaonline.services.DiscoService;
import rivera.sanchez.raul.tiendaonline.services.Validador;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/disco")
public class DiscoController {
    @Autowired
    private DiscoService discoService;

    @GetMapping()
    public ArrayList<DiscoModel> obtenerDiscos() {
        return discoService.obtenerDiscos();
    }

    @GetMapping("{parametroBusqueda}")
    public Object obtenerDiscoporId(@PathVariable("parametroBusqueda") String parametroBusqueda) {
        try {
            Long id = Long.parseLong(parametroBusqueda);
            return discoService.obtenerDiscoPorId(id);
        } catch (NumberFormatException e) {
            return discoService.obtenerDiscoPorTitulo(parametroBusqueda);
        }
    }

    @GetMapping("/query")
    public ArrayList<DiscoModel> obtenerDiscoPorTituloPistasAutorAnyo(@RequestParam(required = false) String titulo,
                                                                      @RequestParam(required = false) String autor,
                                                                      @RequestParam(required = false) Integer pistas,
                                                                      @RequestParam(required = false) Integer anyo) {
        if(titulo != null) {
            return discoService.obtenerDiscoPorTitulo(titulo);
        } else if(autor != null) {
            return discoService.obtenerDiscoPorAutor(autor);
        } else if(pistas != null) {
            return discoService.obtenerDiscoPorPistas(pistas);
        } else if(anyo != null) {
            return discoService.obtenerDiscoPorAnyo(anyo);
        } else {
            return null;
        }
    }

    @PostMapping()
    public String agregarDisco(@RequestBody DiscoModel disco) {

       String mensajeValidacion = Validador.validarCamposDiscos(disco.getTitulo(), disco.getAutor(), disco.getPistas(), disco.getPrecio(), disco.getAnyo());

       if(mensajeValidacion != null) {
           return mensajeValidacion;
       }

        if (disco.getId() == 0) {
            DiscoModel discoAgregado = discoService.agregarOmodificarDisco(disco);

            if (discoAgregado != null) {
                return "Disco agregado exitosamente con id " + discoAgregado.getId();
            } else {
                return "El disco con el título " + disco.getTitulo() + " ya existe";
            }
        } else {
            DiscoModel discoModificado = discoService.agregarOmodificarDisco(disco);

            if (discoModificado != null) {
                return "Disco modificado exitosamente con id " + discoModificado.getId();
            } else {
                return "El disco con el título " + disco.getTitulo() + " ya existe";
            }
        }
    }

    @DeleteMapping("{id}")
    public String eliminarDisco(@PathVariable Long id) {
        boolean eliminado = discoService.eliminarDisco(id);

        if(eliminado) {
            return "Disco eliminado";
        } else {
            return "El disco con la id " + id + " no existe";
        }
    }
}