package com.github.sanchezih.agenda.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.sanchezih.agenda.exception.ResourceNotFoundException;
import com.github.sanchezih.agenda.model.Persona;
import com.github.sanchezih.agenda.repository.PersonaRepository;

@RestController
@RequestMapping("/api/v1/personas")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonaController {

	@Autowired
	private PersonaRepository personaRepository;

	/**
	 * Este metodo sirve para listar todos los empleados
	 * 
	 * @return
	 */
	@GetMapping
	public List<Persona> listarTodosLosEmpleados() {
		return personaRepository.findAll();
	}

	/**
	 * Este metodo sirve para guardar el empleado
	 * 
	 * @param empleado
	 * @return
	 */
	@PostMapping
	public Persona guardarEmpleado(@RequestBody Persona empleado) {
		return personaRepository.save(empleado);
	}

	/**
	 * Este metodo sirve para buscar un empleado
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Persona> obtenerEmpleadoPorId(@PathVariable Long id) {
		Persona empleado = personaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID : " + id));
		return ResponseEntity.ok(empleado);
	}

	/**
	 * Este metodo sirve para actualizar empleado
	 * 
	 * @param id
	 * @param detallesEmpleado
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Persona> actualizarEmpleado(@PathVariable Long id, @RequestBody Persona detallesEmpleado) {
		Persona empleado = personaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID : " + id));

		empleado.setNombre(detallesEmpleado.getNombre());
		empleado.setApellido(detallesEmpleado.getApellido());
		empleado.setEmail(detallesEmpleado.getEmail());

		Persona empleadoActualizado = personaRepository.save(empleado);
		return ResponseEntity.ok(empleadoActualizado);
	}

	/**
	 * Este metodo sirve para eliminar un empleado
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> eliminarEmpleado(@PathVariable Long id) {
		Persona empleado = personaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID : " + id));

		personaRepository.delete(empleado);
		Map<String, Boolean> respuesta = new HashMap<>();
		respuesta.put("eliminar", Boolean.TRUE);
		return ResponseEntity.ok(respuesta);
	}
}