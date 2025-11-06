package dev.java10x.CadastroDeNinjas.Ninjas;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ninjas/ui")
public class NinjaControllerUi {

    private final NinjaService ninjaService;

    public NinjaControllerUi(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    @GetMapping("/listar")
    public String listarNinjas(Model model) {
        List<NinjaDTO> ninjas =  ninjaService.listarNinjas();
        model.addAttribute("ninjas", ninjas);
        return "listarNinjas"; // Tem que retornar o nome da pagina que renderiza
    }

    @GetMapping("/deletar/{id}")
    public String deletarNinjaPorId(@PathVariable Long id) {
        ninjaService.deletarNinjaPorId(id);
        return "redirect:/ninjas/ui/listar";
    }

    @GetMapping("/listar/{id}")
    public String listarNinjasPorId(@PathVariable Long id, Model model) {
        NinjaDTO ninja =  ninjaService.listarNinjasPorId(id);
        if (ninja !=null) {
            model.addAttribute("ninja", ninja);
            return "detalhesninja";
        } else {
            model.addAttribute("mensagem", "Ninja não encontrado");
            return "listarNinjas";
        }
    }

    @GetMapping("/adicionar")
    public String mostrarFormularioAdicionarNinja(Model model) {
        model.addAttribute("ninja", new NinjaDTO());
        return "adicionarNinja";
    }

    @PostMapping("/salvar")
    public String salvarNinja(@ModelAttribute NinjaDTO ninja, RedirectAttributes redirectAttributes) {
        ninjaService.criarNinja(ninja);
        redirectAttributes.addFlashAttribute("mensagem", "Ninja cadastrado com sucesso!");
        return "redirect:/ninjas/ui/listar";
    }

    // Mostra o formulário de alteração de Ninja
    @GetMapping("/alterar/{id}")
    public String mostrarFormularioAlterarNinja(@PathVariable Long id, Model model) {
        NinjaDTO ninja = ninjaService.listarNinjasPorId(id);
        model.addAttribute("ninja", ninja);
        return "alterarNinja"; // nome do template HTML (ex: alterarninja.html)
    }

    // Recebe o POST do formulário e salva a alteração
    @PostMapping("/alterar/{id}")
    public String alterarNinja(@PathVariable Long id, @ModelAttribute("ninja") NinjaDTO ninjaDTO, RedirectAttributes redirectAttributes) {

        NinjaDTO ninjaAtualizado = ninjaService.atualizarNinja(id, ninjaDTO);

        if (ninjaAtualizado != null) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Ninja alterado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro: Ninja não encontrado!");
        }

        return "redirect:/ninjas/ui/listar"; // redireciona para a lista de ninjas (ajuste se o seu caminho for diferente)
    }




}