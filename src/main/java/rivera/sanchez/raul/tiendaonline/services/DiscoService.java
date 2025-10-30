package rivera.sanchez.raul.tiendaonline.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rivera.sanchez.raul.tiendaonline.models.DiscoModel;
import rivera.sanchez.raul.tiendaonline.repositories.DiscoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscoService {

    @Autowired
    private DiscoRepository discoRepository;

    public ArrayList<DiscoModel> obtenerDiscos() {
        return (ArrayList<DiscoModel>) discoRepository.findAll();
    }

    public Optional<DiscoModel> obtenerDiscoPorId(Long id) {
        return discoRepository.findById(id);
    }

    public ArrayList<DiscoModel> obtenerDiscoPorTitulo(String titulo) {
        return discoRepository.findByTitulo(titulo);
    }

    public ArrayList<DiscoModel> obtenerDiscoPorPistas(int pistas) {
        return discoRepository.findByPistas(pistas);
    }

    public ArrayList<DiscoModel> obtenerDiscoPorAutor(String autor) {
        return discoRepository.findByAutor(autor);
    }

    public ArrayList<DiscoModel> obtenerDiscoPorAnyo(int anyo) {
        return discoRepository.findByAnyo(anyo);
    }

    public List<DiscoModel> filtrarDiscos(Long idDisco, String titulo, String autor, Integer anyo, Integer pistas) {
        List<DiscoModel> discosEncontrados = obtenerDiscos();

        return discosEncontrados.stream()
                .filter(disco -> idDisco == null || idDisco.equals(disco.getId()))
                .filter(disco -> titulo == null || titulo.isEmpty() || disco.getTitulo() != null && disco.getTitulo().equalsIgnoreCase(titulo))
                .filter(disco -> autor == null || autor.isEmpty() || disco.getAutor() != null && disco.getAutor().equalsIgnoreCase(autor))
                .filter(disco -> anyo == null || anyo.equals(disco.getAnyo()))
                .filter(disco -> pistas == null || pistas.equals(disco.getPistas()))
                .collect(Collectors.toList());
    }

    public DiscoModel agregarOmodificarDisco(DiscoModel disco) {
        ArrayList<DiscoModel> discosExistentes = discoRepository.findByTitulo(disco.getTitulo());

        for (DiscoModel d : discosExistentes) {
            if (d.getId() != disco.getId()) {
                return null;
            }
        }

        return discoRepository.save(disco);
    }

    public boolean eliminarDisco(Long id) {
        Optional<DiscoModel> disco = discoRepository.findById(id);
        if (disco.isPresent()) {
            discoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
