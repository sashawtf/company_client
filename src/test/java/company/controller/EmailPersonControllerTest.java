package company.controller;

import company.entity.EmailPerson;
import company.entity.Person;
import company.repository.EmailPersonRepository;
import company.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmailPersonController.class)
@AutoConfigureMockMvc
public class EmailPersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailPersonRepository emailPersonRepository;

    @MockBean
    private PersonRepository personRepository;

    @Test
    @WithMockUser
    public void testShowAllEmailPersons() throws Exception {
        List<EmailPerson> emailPersons = new ArrayList<>();
        when(emailPersonRepository.findAll()).thenReturn(emailPersons);
        List<Person> persons = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(persons);

        mockMvc.perform(MockMvcRequestBuilders.get("/email/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("emailPersonList"))
                .andExpect(model().attribute("emails", emailPersons))
                .andExpect(model().attribute("persons", persons));
    }

    @Test
    @WithMockUser
    public void testShowAddEmailPersonForm() throws Exception {
        List<Person> persons = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(persons);

        mockMvc.perform(MockMvcRequestBuilders.get("/email/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("emailPersonForm"))
                .andExpect(model().attributeExists("emailPerson"))
                .andExpect(model().attribute("persons", persons));
    }

    @Test
    @WithMockUser
    public void testShowEditEmailPersonForm() throws Exception {
        Long id = 1L;
        EmailPerson emailPerson = new EmailPerson();
        emailPerson.setId(id);
        List<Person> persons = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(persons);
        when(emailPersonRepository.findById(id)).thenReturn(Optional.of(emailPerson));

        mockMvc.perform(MockMvcRequestBuilders.get("/email/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("emailPersonEditForm"))
                .andExpect(model().attribute("emailPerson", emailPerson))
                .andExpect(model().attribute("persons", persons));
    }

    @Test
    @WithMockUser
    public void testDeleteEmailPerson() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/email/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/email/all"));
        verify(emailPersonRepository, times(1)).deleteById(id);
    }
}
