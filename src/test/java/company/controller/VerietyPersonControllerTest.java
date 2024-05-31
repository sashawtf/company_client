package company.controller;

import company.entity.VerietyPerson;
import company.repository.PersonRepository;
import company.repository.VerietyPersonRepository;
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

@WebMvcTest(VerietyPersonController.class)
@AutoConfigureMockMvc
public class VerietyPersonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VerietyPersonRepository verietyPersonRepository;
    @MockBean
    private PersonRepository personRepository;
    @Test
    @WithMockUser
    public void testShowAllVerieties() throws Exception {
        List<VerietyPerson> verieties = new ArrayList<>();
        when(verietyPersonRepository.findAll()).thenReturn(verieties);
        mockMvc.perform(MockMvcRequestBuilders.get("/veriety/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("verietyPersonList"))
                .andExpect(model().attribute("verieties", verieties));
    }
    @Test
    @WithMockUser
    public void testShowAddVerietyPersonForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/veriety/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("verietyPersonForm"))
                .andExpect(model().attributeExists("verietyPerson"));
    }
    @Test
    @WithMockUser
    public void testShowEditVerietyPersonForm() throws Exception {
        Long id = 1L;
        VerietyPerson verietyPerson = new VerietyPerson();
        verietyPerson.setId(id);
        when(verietyPersonRepository.findById(id)).thenReturn(Optional.of(verietyPerson));
        mockMvc.perform(MockMvcRequestBuilders.get("/veriety/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("verietyPersonEditForm"))
                .andExpect(model().attribute("verietyPerson", verietyPerson));
    }
    @Test
    @WithMockUser
    public void testDeleteVerietyPerson() throws Exception {
        Long id = 1L;
        when(personRepository.existsByVerietyId(id)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.get("/veriety/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/veriety/all"));
        verify(verietyPersonRepository, times(1)).deleteById(id);
    }
    @Test
    @WithMockUser
    public void testDeleteVerietyPersonWithExistingRelationship() throws Exception {
        Long id = 1L;
        List<VerietyPerson> verieties = new ArrayList<>();
        when(verietyPersonRepository.findAll()).thenReturn(verieties);
        when(personRepository.existsByVerietyId(id)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/veriety/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("verietyPersonList"))
                .andExpect(model().attribute("error", "Нельзя удалить, есть связь."))
                .andExpect(model().attribute("verieties", verieties));
        verify(verietyPersonRepository, never()).deleteById(id);
    }
}
