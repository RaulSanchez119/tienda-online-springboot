package rivera.sanchez.raul.tiendaonline.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rivera.sanchez.raul.tiendaonline.enums.Formato;
import rivera.sanchez.raul.tiendaonline.models.DiscoModel;
import rivera.sanchez.raul.tiendaonline.models.PeliculaModel;
import rivera.sanchez.raul.tiendaonline.repositories.PeliculaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeliculaService {
    @Autowired
    private PeliculaRepository peliculaRepository;

    public ArrayList<PeliculaModel> obtenerPeliculas() {
        return (ArrayList<PeliculaModel>) peliculaRepository.findAll();
    }

    public Optional<PeliculaModel> obtenerPeliculaPorId(Long id) {
        return peliculaRepository.findById(id);
    }

    public ArrayList<PeliculaModel> obtenerPeliculasPorTitulo(String titulo) {
        return peliculaRepository.findByTitulo(titulo);
    }

    public ArrayList<PeliculaModel> obtenerPeliculasPorDirector(String director) {
        return peliculaRepository.findByDirector(director);
    }

    public ArrayList<PeliculaModel> obtenerPeliculasPorAnyo(int anyo) {
        return peliculaRepository.findByAnyo(anyo);
    }

    public ArrayList<PeliculaModel> obtenerPeliculasPorFormato(Formato formato) {
        return peliculaRepository.findByFormato(formato);
    }

    public List<PeliculaModel> filtrarPeliculas(Long idPelicula, String titulo, Formato formato, Integer anyo, String director) {
        List<PeliculaModel> peliculasEncontradas = obtenerPeliculas();

        return peliculasEncontradas.stream()
                .filter(pelicula -> idPelicula == null || idPelicula.equals(pelicula.getId()))
                .filter(pelicula -> titulo == null || titulo.isEmpty() || pelicula.getTitulo().equalsIgnoreCase(titulo))
                .filter(pelicula -> formato == null || formato.equals(pelicula.getFormato()))
                .filter(pelicula -> anyo == null || anyo.equals(pelicula.getAnyo()))
                .filter(pelicula -> director == null || director.isEmpty() || pelicula.getDirector().equalsIgnoreCase(director))
                .collect(Collectors.toList());
    }

    public PeliculaModel agregarOmodificarPelicula(PeliculaModel pelicula) {
        ArrayList<PeliculaModel> peliculasExistentes = peliculaRepository.findByTitulo(pelicula.getTitulo());

        for (PeliculaModel p : peliculasExistentes) {
            if (p.getId() != pelicula.getId()) {
                return null;
            }
        }

        return peliculaRepository.save(pelicula);
    }


    public boolean eliminarPelicula(Long id) {
        Optional<PeliculaModel> pelicula = peliculaRepository.findById(id);

        if (pelicula.isPresent()) {
            peliculaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
