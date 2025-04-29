package br.com.memelandia.service;

import br.com.memelandia.entities.Categoria;
import br.com.memelandia.entities.Meme;
import br.com.memelandia.entities.Usuario;
import br.com.memelandia.repositori.RepositoriCategoria;
import br.com.memelandia.repositori.RepositoriMeme;
import br.com.memelandia.repositori.RepositoriUsuario;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.time.LocalDate;



/**
 * @author fsdney
 */


@Service
public class ServiceMeme {

    private static final Logger logger = LoggerFactory.getLogger(ServiceMeme.class);

    private final MeterRegistry meterRegistry;
    private final RepositoriMeme repositoriMeme;
    private final RestTemplate restTemplate;

    private static final String URL_USUARIO_SERVICE = "http://localhost:8080/usuario_service/";
    private static final String URL_CATEGORIA_SERVICE = "http://localhost:8081/categoria_service/";

    public ServiceMeme(MeterRegistry meterRegistry, RepositoriMeme repositoriMeme) {
        this.meterRegistry = meterRegistry;
        this.repositoriMeme = repositoriMeme;
        this.restTemplate = new RestTemplate();
    }

    public List<Meme> listarTodosMemes() {
        logger.info("Recebida requisição para listar todos os memes.");
        meterRegistry.counter("meme.listar.todas.chamadas").increment();

        long start = System.currentTimeMillis();
        List<Meme> memes = repositoriMeme.findAll();
        meterRegistry.timer("meme.listar.todas.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);

        if (memes.isEmpty()) {
            logger.warn("A lista de memes está vazia.");
            meterRegistry.counter("meme.listar.todas.vazio").increment();
        } else {
            logger.info("Total de memes encontrados: {}", memes.size());
            meterRegistry.counter("meme.listar.todas.sucesso").increment();
            meterRegistry.gauge("meme.listar.todas.quantidade", memes, List::size);
        }

        return memes;
    }

    public Optional<Meme> criarMeme(Meme meme) {
        logger.info("Recebida requisição para criar um novo meme.");
        meterRegistry.counter("meme.criar.chamadas").increment();

        long start = System.currentTimeMillis();

        try {
            restTemplate.getForObject(URL_CATEGORIA_SERVICE + "nome/" + meme.getCategoriaName(), Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Categoria '{}' não encontrada.", meme.getCategoriaName());
            meterRegistry.counter("meme.criar.categoria.naoencontrada").increment();
            throw new RuntimeException("Categoria não encontrada: " + meme.getCategoriaName());
        }

        try {
            restTemplate.getForObject(URL_USUARIO_SERVICE + "nome/" + meme.getUsuarioName(), Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Usuário '{}' não encontrado.", meme.getUsuarioName());
            meterRegistry.counter("meme.criar.usuario.naoencontrado").increment();
            throw new RuntimeException("Usuário não encontrado: " + meme.getUsuarioName());
        }

        meme.setDataCadastro(LocalDate.now());
        Meme salvo = repositoriMeme.save(meme);

        logger.info("Meme criado com sucesso: {}", salvo);
        meterRegistry.counter("meme.criar.sucesso").increment();
        meterRegistry.timer("meme.criar.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);

        return Optional.of(salvo);
    }



    public Optional<Meme> buscarMemePorId(Long id) {
        logger.info("Recebida requisição para buscar meme com ID: {}", id);
        meterRegistry.counter("meme.buscar.id.chamadas").increment();

        long start = System.currentTimeMillis();
        Optional<Meme> meme = repositoriMeme.findById(id);
        meterRegistry.timer("meme.buscar.id.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);

        if (meme.isPresent()) {
            logger.info("Meme encontrado: {}", meme.get());
            meterRegistry.counter("meme.buscar.id.sucesso").increment();
        } else {
            logger.warn("Meme com ID {} não encontrado.", id);
            meterRegistry.counter("meme.buscar.id.naoencontrada").increment();
        }

        return meme;
    }
    
    public Optional<Meme> buscarMemePorNome(String name) {
        logger.info("Recebida requisição para buscar meme com nome: {}", name);
        meterRegistry.counter("meme.buscar.nome.chamadas").increment();

        long start = System.currentTimeMillis();
        Optional<Meme> meme = repositoriMeme.findByName(name);
        meterRegistry.timer("meme.buscar.nome.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);

        if (meme.isPresent()) {
            logger.info("Meme encontrado: {}", meme.get());
            meterRegistry.counter("meme.buscar.nome.sucesso").increment();
        } else {
            logger.warn("Meme com nome {} não encontrado.", name);
            meterRegistry.counter("meme.buscar.nome.naoencontrada").increment();
        }

        return meme;
    }
    

    public boolean deletarMemePorId(Long id) {
        logger.info("Recebida requisição para deletar meme com ID: {}", id);
        meterRegistry.counter("meme.deletar.id.chamadas").increment();

        long start = System.currentTimeMillis();

        Optional<Meme> memeExistente = repositoriMeme.findById(id);
        if (memeExistente.isPresent()) {
            repositoriMeme.deleteById(id);
            logger.info("Meme com ID {} deletado com sucesso.", id);
            meterRegistry.counter("meme.deletar.id.sucesso").increment();
            meterRegistry.timer("meme.deletar.id.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
            return true;
        } else {
            logger.warn("Meme com ID {} não encontrado.", id);
            meterRegistry.counter("meme.deletar.id.naoencontrada").increment();
            meterRegistry.timer("meme.deletar.id.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
            return false;
        }
    }
    
    public boolean deletarMemePorNome(String name) {
        logger.info("Recebida requisição para deletar meme com nome: {}", name);
        meterRegistry.counter("meme.deletar.nome.chamadas").increment();

        long start = System.currentTimeMillis();

        Optional<Meme> memeExistente = repositoriMeme.findByName(name);
        if (memeExistente.isPresent()) {
       	    repositoriMeme.delete(memeExistente.get());
            logger.info("Meme com nome {} deletado com sucesso.", name);
            meterRegistry.counter("meme.deletar.nome.sucesso").increment();
            meterRegistry.timer("meme.deletar.nome.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
            return true;
        } else {
            logger.warn("Meme com nome {} não encontrado.", name);
            meterRegistry.counter("meme.deletar.nome.naoencontrada").increment();
            meterRegistry.timer("meme.deletar.nome.tempo").record(System.currentTimeMillis() - start, TimeUnit.MILLISECONDS);
            return false;
        }
    }


    public Meme obterMemeDoDia() {
        logger.info("Selecionando meme do dia.");
        return Optional.ofNullable(repositoriMeme.findRandomMeme())
                .orElseThrow(() -> new RuntimeException("Nenhum meme encontrado no banco de dados."));
    }
}
