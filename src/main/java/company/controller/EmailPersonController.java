package company.controller;
import company.entity.EmailPerson;
import company.repository.EmailPersonRepository;
import company.repository.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/email")
public class EmailPersonController {

    private final EmailPersonRepository emailPersonRepository;
    private final PersonRepository personRepository;

    @Autowired
    public EmailPersonController(EmailPersonRepository emailPersonRepository, PersonRepository personRepository) {
        this.emailPersonRepository = emailPersonRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("emailPerson", new EmailPerson());
        model.addAttribute("persons", personRepository.findAll()); // Получаем список всех клиентов
        return "emailPersonForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("emailPerson") EmailPerson emailPerson, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("persons", personRepository.findAll());
            return "emailPersonForm";
        }
        emailPersonRepository.save(emailPerson);
        return "redirect:/email/all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("emails", emailPersonRepository.findAll());
        model.addAttribute("persons", personRepository.findAll());
        return "emailPersonList";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        EmailPerson emailPerson = emailPersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid emailPerson Id:" + id));
        model.addAttribute("emailPerson", emailPerson);
        model.addAttribute("persons", personRepository.findAll()); // Получаем список всех клиентов
        return "emailPersonEditForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("emailPerson") EmailPerson emailPerson, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("persons", personRepository.findAll());
            return "emailPersonEditForm";
        }
        emailPerson.setId(id);
        emailPersonRepository.save(emailPerson);
        return "redirect:/email/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        emailPersonRepository.deleteById(id);
        return "redirect:/email/all";
    }
}

