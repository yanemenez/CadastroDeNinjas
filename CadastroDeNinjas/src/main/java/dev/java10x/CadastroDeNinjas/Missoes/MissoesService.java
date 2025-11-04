package dev.java10x.CadastroDeNinjas.Missoes;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MissoesService {

    private final MissoesRepository missoesRepository;
    private final MissoesMapper missoesMapper;

    public MissoesService(MissoesRepository missoesRepository, MissoesMapper missoesMapper) {
        this.missoesRepository = missoesRepository;
        this.missoesMapper = missoesMapper;
    }

    // Listar todas as missões
    public List<MissoesDTO> listarMissoes() {
        List<MissoesModel> missoes = missoesRepository.findAll();
        return missoes.stream()
                .map(missoesMapper::map)
                .collect(Collectors.toList());
    }

    // Listar missão por ID
    public MissoesDTO listarMissoesPorId(Long id) {
        Optional<MissoesModel> missaoPorId = missoesRepository.findById(id);
        return missaoPorId.map(missoesMapper::map).orElse(null);
    }

    // Criar nova missão
    public MissoesDTO criarMissao(MissoesDTO missoesDTO) {
        MissoesModel missao = missoesMapper.map(missoesDTO);
        missao.setId(null); // garante que cria uma nova
        missao = missoesRepository.save(missao);
        return missoesMapper.map(missao);
    }

    // Atualizar missão existente
    public MissoesDTO atualizarMissao(Long id, MissoesDTO missoesDTO) {
        Optional<MissoesModel> existente = missoesRepository.findById(id);
        if (existente.isPresent()) {
            MissoesModel missao = existente.get();
            missao.setNome(missoesDTO.getNome());
            missao.setDificuldade(missoesDTO.getDificuldade());
            missao = missoesRepository.save(missao);
            return missoesMapper.map(missao);
        } else {
            return null;
        }
    }

    // Deletar missão
    public void deletarMissao(Long id) {
        missoesRepository.deleteById(id);
    }
}