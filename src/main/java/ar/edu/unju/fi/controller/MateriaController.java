package ar.edu.unju.fi.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.unju.fi.collections.ListadoCarrera;
import ar.edu.unju.fi.collections.ListadoDocente;
import ar.edu.unju.fi.collections.ListadoMateria;
import ar.edu.unju.fi.model.Materia;
@Controller
public class MateriaController {
	@Autowired
	Materia nuevaMateria = new Materia();
	
	@GetMapping("/formularioMaterias")
    public ModelAndView getFormularioMaterias() {
		//vista de formDocente
		ModelAndView modelView = new ModelAndView("formularioMaterias");
		modelView.addObject("nuevaMateria", nuevaMateria);
		modelView.addObject("listadoDocente", ListadoDocente.listarDocentes());
		modelView.addObject("listadoCarrera", ListadoCarrera.listarCarreras());	
        return modelView;
    }
	
	@PostMapping("/guardarMateria")
	public ModelAndView saveMateria(@ModelAttribute("nuevaMateria") Materia materiaAGuardar) {
		//guardar
		materiaAGuardar.setDocente(ListadoDocente.buscarDocentePorLegajo(materiaAGuardar.getDocente().getLegajo()));
		materiaAGuardar.setCarrera(ListadoCarrera.buscarCarreraPorCodigo(materiaAGuardar.getCarrera().getCodigo()));
		ListadoMateria.agregarMateria(materiaAGuardar);
		//mostrar el listado
		ModelAndView modelView = new ModelAndView("listaDeMaterias");
		modelView.addObject("listadoMateria", ListadoMateria.listarMaterias());	
		return modelView;	
	}
	
	@GetMapping("/borrarMateria/{codigo}")
	public ModelAndView deleteMateriaDelListado(@PathVariable(name="codigo") String codigo) {
		//borrar
		ListadoMateria.eliminarMateria(codigo);
		//mostrar el nuevo listado
		ModelAndView modelView = new ModelAndView("listaDeMaterias");
		modelView.addObject("listadoMateria", ListadoMateria.listarMaterias());	
		return modelView;		
	}
	
	@GetMapping("/modificarMateria/{codigo}")
	public ModelAndView editMateria(@PathVariable(name="codigo") String codigo) {
		//seleccionar el docente para modificar
		Materia materiaAModificar = ListadoMateria.buscarMateriaPorCodigo(codigo);
		
		//mostrar el formulario de modificacion
		ModelAndView modelView = new ModelAndView("formularioMaterias");
		modelView.addObject("nuevaMateria", materiaAModificar);	
		modelView.addObject("listadoDocente", ListadoDocente.listarDocentes());
		modelView.addObject("listadoCarrera", ListadoCarrera.listarCarreras());	
		modelView.addObject("flag", true);
		return modelView;		
		}
	
	@PostMapping("/modificarMateria")
	public ModelAndView updateMateria(@ModelAttribute("nuevaMateria") Materia materiaModificada) {
					
		//modificar el docente
		materiaModificada.setDocente(ListadoDocente.buscarDocentePorLegajo(materiaModificada.getDocente().getLegajo()));
		materiaModificada.setCarrera(ListadoCarrera.buscarCarreraPorCodigo(materiaModificada.getCarrera().getCodigo()));
		ListadoMateria.modificarMateria(materiaModificada);
		
		//mostrar el listado
		ModelAndView modelView = new ModelAndView("listaDeMaterias");
		modelView.addObject("listadoMateria", ListadoMateria.listarMaterias());	
		return modelView;		
	}
	
	@GetMapping("/materias")
	public ModelAndView showMaterias() {
		//mostrar el listado
		ModelAndView modelView = new ModelAndView("listaDeMaterias");
		modelView.addObject("listadoMateria", ListadoMateria.listarMaterias());	
		return modelView;		
	}
}