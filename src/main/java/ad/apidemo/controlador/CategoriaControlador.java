package ad.apidemo.controlador;

import ad.apidemo.modelo.Categoria;
import ad.apidemo.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaControlador {

    @Autowired
    private CategoriaRepositorio categegoriaRepositorio;

    //todas las categorias (GET)
    @GetMapping
    public List<Categoria> obtenerCategorias() {
        return categegoriaRepositorio.findAll();
    }

    //obtener una categoria por su ID (GET)
    @GetMapping("/{id}")
    public Categoria obtenerCategoriaPorId(@PathVariable Long id) {
        Optional<Categoria> resultado = categegoriaRepositorio.findById(id);
        return resultado.orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        //return resultado.get();
    }

    //crear categoria (POST)
    @PostMapping
    public Categoria crearCategoria(@RequestBody Categoria categoria) { //le pasamos un requeststBody que lo convierte en un JSON
        return categegoriaRepositorio.save(categoria);
    }

    //actualizar una categoria (PUT)
    @PutMapping("/{id}")
    public Categoria actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categegoriaRepositorio.findById(id).map(categoriaTemp -> {
            categoriaTemp.setNombre(categoria.getNombre());
            return categegoriaRepositorio.save(categoriaTemp);
        }).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }

    //eliminar una categoria (DELETE)
    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable Long id) {
        categegoriaRepositorio.deleteById(id);
    }
}
