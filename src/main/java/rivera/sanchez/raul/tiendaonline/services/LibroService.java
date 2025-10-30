package rivera.sanchez.raul.tiendaonline.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rivera.sanchez.raul.tiendaonline.models.DiscoModel;
import rivera.sanchez.raul.tiendaonline.models.LibroModel;
import rivera.sanchez.raul.tiendaonline.repositories.LibroRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    public ArrayList<LibroModel> obtenerLibros(){
        return (ArrayList<LibroModel>) libroRepository.findAll();
    }

    public Optional<LibroModel> obtenerLibroPorId(Long id){
        return libroRepository.findById(id);
    }

    public ArrayList<LibroModel> obtenerLibroPorTitulo(String titulo){
        return libroRepository.findByTitulo(titulo);
    }

    public ArrayList<LibroModel> obtenerLibroPorAutor(String autor){
        return libroRepository.findByAutor(autor);
    }

    public ArrayList<LibroModel> obtenerLibroPorEditorial(String editorial){
        return libroRepository.findByEditorial(editorial);
    }

    public ArrayList<LibroModel> obtenerLibroPorIsbn(String isbn){
        return libroRepository.findByIsbn(isbn);
    }

    public LibroModel agregarOmodificarLibro(LibroModel libro) {
        ArrayList<LibroModel> librosExistentes = libroRepository.findByTitulo(libro.getTitulo());

        for (LibroModel l : librosExistentes) {
            if (l.getId() != libro.getId()) {
                return null;
            }
        }

        return libroRepository.save(libro);
    }

    public List<LibroModel> filtrarLibros(Long idLibro, String titulo, String autor, String isbn, String editorial) {
        List<LibroModel> librosEncontrados = obtenerLibros();

        return librosEncontrados.stream()
                .filter(libro -> idLibro == null || idLibro.equals(libro.getId()))
                .filter(libro -> titulo == null || titulo.isEmpty() || libro.getTitulo() != null && libro.getTitulo().equalsIgnoreCase(titulo))
                .filter(libro -> autor == null || autor.isEmpty() || libro.getAutor() != null && libro.getAutor().equalsIgnoreCase(autor))
                .filter(libro -> isbn == null || isbn.isEmpty() || libro.getIsbn() != null && libro.getIsbn().equalsIgnoreCase(isbn))
                .filter(libro -> editorial == null || editorial.isEmpty() || libro.getEditorial() != null && libro.getEditorial().equalsIgnoreCase(editorial))
                .collect(Collectors.toList());
    }

    public boolean eliminarLibro(Long id){
     Optional<LibroModel> libro = libroRepository.findById(id);
     if (libro.isPresent()) {
         libroRepository.deleteById(id);
         return true;
     } else {
         return false;
     }
    }
}
