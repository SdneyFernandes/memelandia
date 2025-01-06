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

 @Autowired
 private ServiceMeme serviceMeme;

 @GetMapping
 public ResponseEntity<List<Meme>> listarTodosMemes() {
     
     return ResponseEntity.ok(serviceMeme.listarTodosMemes());
 }

 @PostMapping
 public ResponseEntity<Meme> criarMeme(@RequestBody Meme meme) {
     Meme novoMeme = serviceMeme.criarMeme(meme);
     return ResponseEntity.status(HttpStatus.CREATED).body(novoMeme);
 }

 @GetMapping("/{id}")
 public ResponseEntity<Meme> buscarMemePorId(@PathVariable Long id) {
     return serviceMeme.buscarMemePorId(id)
             .map(ResponseEntity::ok)
             .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
 }

 @DeleteMapping("/{id}")
 public ResponseEntity<Void> deletarMeme(@PathVariable Long id) {
     serviceMeme.deletarMeme(id);
     return ResponseEntity.noContent().build();
 }
 
 @GetMapping("/meme-do-dia")
 public ResponseEntity<Meme> obterMemeDoDia() {
     Meme meme = serviceMeme.obterMemeDoDia();
     return ResponseEntity.ok(meme);
 }
 
 @GetMapping("/test")
 public ResponseEntity<String> test() {
     return ResponseEntity.ok("Serviço está rodando");
 }
}