package company.controller;

import static org.junit.jupiter.api.Assertions.*;

import company.entity.Person;
import company.entity.StatusPerson;
import company.entity.VerietyPerson;
import company.repository.*;
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

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private VerietyPersonRepository verietyPersonRepository;

    @MockBean
    private StatusPersonRepository statusPersonRepository;

    @MockBean
    private PhonePersonRepository phonePersonRepository;

    @MockBean
    private EmailPersonRepository emailPersonRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testShowAllPersons() throws Exception {
        List<Person> persons = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(persons);
        List<VerietyPerson> varieties = new ArrayList<>();
        when(verietyPersonRepository.findAll()).thenReturn(varieties);
        List<StatusPerson> statuses = new ArrayList<>();
        when(statusPersonRepository.findAll()).thenReturn(statuses);

        mockMvc.perform(MockMvcRequestBuilders.get("/person/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("personList"))
                .andExpect(model().attribute("persons", persons))
                .andExpect(model().attribute("verieties", varieties))
                .andExpect(model().attribute("statuses", statuses));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testShowAddPersonForm() throws Exception {
        List<VerietyPerson> varieties = new ArrayList<>();
        when(verietyPersonRepository.findAll()).thenReturn(varieties);
        List<StatusPerson> statuses = new ArrayList<>();
        when(statusPersonRepository.findAll()).thenReturn(statuses);

        mockMvc.perform(MockMvcRequestBuilders.get("/person/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("personForm"))
                .andExpect(model().attributeExists("person"))
                .andExpect(model().attribute("verieties", varieties))
                .andExpect(model().attribute("statuses", statuses));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testShowEditPersonForm() throws Exception {
        Long id = 1L;
        Person person = new Person();
        person.setId(id);
        List<VerietyPerson> varieties = new ArrayList<>();
        when(verietyPersonRepository.findAll()).thenReturn(varieties);
        List<StatusPerson> statuses = new ArrayList<>();
        when(statusPersonRepository.findAll()).thenReturn(statuses);
        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        mockMvc.perform(MockMvcRequestBuilders.get("/person/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("personEditForm"))
                .andExpect(model().attribute("person", person))
                .andExpect(model().attribute("verieties", varieties))
                .andExpect(model().attribute("statuses", statuses));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeletePerson() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/person/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/person/all"));
        verify(personRepository, times(1)).deleteById(id);
    }
}
