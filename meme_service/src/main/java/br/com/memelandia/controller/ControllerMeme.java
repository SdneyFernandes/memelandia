package br.com.memelandia.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.memelandia.entities.Meme;
import br.com.memelandia.service.ServiceMeme;

/**
 * @author fsdney
 */

@RestController
@RequestMapping("/meme_service")
public class ControllerMeme {

 private static final Logger logger = LoggerFactory.getLogger(ControllerMeme.class);

 @Autowired
 private ServiceMeme serviceMeme;

 @GetMapping
 public ResponseEntity<List<Meme>> listarTodosMemes() {
     logger.info("Recebida requisição para listar todos os memes.");
     return ResponseEntity.ok(serviceMeme.listarTodosMemes());
 }

 @PostMapping
 public ResponseEntity<Meme> criarMeme(@RequestBody Meme meme) {
     logger.info("Recebida requisição para criar novo meme: {}", meme);
     Meme novoMeme = serviceMeme.criarMeme(meme);
     return ResponseEntity.status(HttpStatus.CREATED).body(novoMeme);
 }

 @GetMapping("/{id}")
 public ResponseEntity<Meme> buscarMemePorId(@PathVariable Long id) {
     logger.info("Recebida requisição para buscar meme por ID: {}", id);
     return serviceMeme.buscarMemePorId(id)
             .map(ResponseEntity::ok)
             .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
 }

 @DeleteMapping("/{id}")
 public ResponseEntity<Void> deletarMeme(@PathVariable Long id) {
     logger.info("Recebida requisição para deletar meme com ID: {}", id);
     serviceMeme.deletarMeme(id);
     return ResponseEntity.noContent().build();
 }
 
 public ResponseEntity<Meme> obterMemeDoDia() {
     logger.info("Recebida requisição para obter o meme do dia.");
     Meme meme = serviceMeme.obterMemeDoDia();
     return ResponseEntity.ok(meme);
 }
}
