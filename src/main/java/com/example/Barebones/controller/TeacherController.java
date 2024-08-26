package com.example.Barebones.controller;



import com.example.Barebones.service.TeacherService;
import com.example.Barebones.model.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TeacherController {

	private TeacherService teacherService;

	public TeacherController(TeacherService teacherService) {
		super();
		this.teacherService = teacherService;
	}
	
	// handler method to handle list Teachers and return mode and view
	@GetMapping("/teachers")
	public String listTeachers(Model model) {
		System.out.println("IN  TeacherController->listTeachers()");
		model.addAttribute("teachers", teacherService.getAllTeachers());
		return "teachers";
	}
	
	@GetMapping("/teachers/new")
	public String createTeacherForm(Model model) {
		System.out.println("IN  TeacherController->createTeacherForm()");
		// create  object to hold Teacher form data
		Teacher teacher = new Teacher();
		model.addAttribute("teacher", teacher);
		return "create_teacher";
		
	}
	
	@PostMapping("/teachers")
	public String saveStudent(@ModelAttribute("student") Teacher teacher) {
		System.out.println("IN  TeacherController->saveTeacher()");
		teacherService.saveTeacher(teacher);
		return "redirect:/teachers";
	}
	
	@GetMapping("/teachers/edit/{id}")
	public String editTeacherForm(@PathVariable Long id, Model model) {
		System.out.println("IN  TeacherController->editTeacherForm()");
		model.addAttribute("teacher", teacherService.getTeacherById(id));
		return "edit_teacher";
	}

	@PostMapping("/teachers/{id}")
	public String updateTeacher(@PathVariable Long id,
			@ModelAttribute("teacher") Teacher teacher,
			Model model) {
		
		// get teach from database by id
		Teacher existingTeacher = teacherService.getTeacherById(id);
		existingTeacher.setId(id);
		existingTeacher.setFirstName(teacher.getFirstName());
		existingTeacher.setLastName(teacher.getLastName());
		existingTeacher.setEmail(teacher.getEmail());
		
		// save updated teach object
		teacherService.updateTeacher(existingTeacher);
		return "redirect:/teachers";
	}
	
	// handler method to handle delete teacher request
	
	@GetMapping("/teachers/{id}")
	public String deleteTeacher(@PathVariable Long id) {
		System.out.println("IN  TeacherController->deleteTeacher()");
		teacherService.deleteTeacherById(id);
		return "redirect:/teachers";
	}
	
}
