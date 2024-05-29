package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    //método post
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono()
                , medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);

        // retornar 201 created
        // URL donde encontrar al medico
    }

    //método get
    @GetMapping()
    public ResponseEntity<Page<DatoListadoMedico>> listadoMedico(@PageableDefault(size = 5) Pageable paginacion) {
        // return medicoRepository.findAll(paginacion).map(DatoListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatoListadoMedico::new));

    }

    //método put
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono()
                , medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));
    }

    //método delete logico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    //retorna solo un dato en especifico
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedicos = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono()
                , medico.getEspecialidad().toString(), new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedicos);
    }


}//cierre clase

   /* @GetMapping
    public List<DatoListadoMedico> listadoMedico(){
        return medicoRepository.findAll().stream().map(DatoListadoMedico::new).toList();
    }

     //método delete para borrar de la base de datos
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    }
    */