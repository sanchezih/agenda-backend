package com.github.sanchezih.agenda.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
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
import com.github.sanchezih.agenda.model.Contacto;
import com.github.sanchezih.agenda.repository.ContactoRepository;

@RestController
@RequestMapping("/api/v1/personas")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonaController {

	@Autowired
	private ContactoRepository personaRepository;

	/**
	 * Este metodo sirve para listar todos los empleados
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Contacto>> listarTodosLosEmpleados() {
		List<Contacto> res = personaRepository.findAll();
		return ResponseEntity.ok(res);
	}

	/**
	 * Este metodo sirve para guardar el empleado
	 * 
	 * @param contacto
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Contacto> guardarEmpleado(@RequestBody Contacto contacto) {
		Contacto nuevoContacto = personaRepository.save(contacto);
		return ResponseEntity.ok(nuevoContacto);
	}

	
	
	
	
	/**
	 * Retorna un cotacto segun el id recibido como parametro. En caso de no
	 * encontrarlo, retorna HTTP 400
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Contacto> getContactoById(@PathVariable Long id) {
//		Contacto contacto = personaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID : " + id));
//		return ResponseEntity.ok(contacto);

		Optional<Contacto> optionalProduct = personaRepository.findById(id);
		if (optionalProduct.isPresent()) {
			return ResponseEntity.ok(optionalProduct.get());
		} else {
			return ResponseEntity.badRequest().build();
		}

	}

	
	
	
	
	
	
	
	/**
	 * Este metodo sirve para actualizar empleado
	 * 
	 * @param id
	 * @param detallesEmpleado
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Contacto> actualizarEmpleado(@PathVariable Long id, @RequestBody Contacto detallesEmpleado) {
		Contacto empleado = personaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID : " + id));

		empleado.setNombre(detallesEmpleado.getNombre());
		empleado.setApellido(detallesEmpleado.getApellido());
		empleado.setEmail(detallesEmpleado.getEmail());

		Contacto empleadoActualizado = personaRepository.save(empleado);
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
		Contacto empleado = personaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID : " + id));

		personaRepository.delete(empleado);
		Map<String, Boolean> respuesta = new HashMap<>();
		respuesta.put("eliminar", Boolean.TRUE);
		return ResponseEntity.ok(respuesta);
	}
}