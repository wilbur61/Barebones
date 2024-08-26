package com.example.Barebones.service;

import com.example.Barebones.model.Teacher;

import java.util.List;


public interface TeacherService {
	List<Teacher> getAllTeachers();
	
	Teacher saveTeacher(Teacher teacher);
	
	Teacher getTeacherById(Long id);
	
	Teacher updateTeacher(Teacher teacher);
	
	void deleteTeacherById(Long id);
}
