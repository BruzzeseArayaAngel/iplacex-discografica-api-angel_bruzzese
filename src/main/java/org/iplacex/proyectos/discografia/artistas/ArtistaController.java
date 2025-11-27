package org.iplacex.proyectos.discografia.artistas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository repo;

    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista creado = repo.save(artista);
        // 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        List<Artista> artistas = repo.findAll();
        return ResponseEntity.ok(artistas); // 200 OK
    }

    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable String id) {
        return repo.findById(id)
                .<ResponseEntity<Object>>map(artista -> ResponseEntity.ok(artista)) // 200 OK
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND) // 404
                        .body("Artista no encontrado"));
    }

    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(
            @PathVariable String id,
            @RequestBody Artista artista) {

        if (!repo.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND) // 404
                    .body("Artista no encontrado");
        }

        artista.setId(id);
        Artista actualizado = repo.save(artista);
        return ResponseEntity.ok(actualizado); // 200 OK
    }

    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable String id) {

        if (!repo.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND) // 404
                    .body("Artista no encontrado");
        }

        repo.deleteById(id);
        // 204 No Content (sin cuerpo)
        return ResponseEntity.noContent().build();
    }
}