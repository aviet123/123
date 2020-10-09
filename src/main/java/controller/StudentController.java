package controller;

import model.Student;
import model.StudentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.student.IStudentService;

import java.io.File;
import java.io.IOException;

@Controller
public class StudentController {
    @Autowired
    private Environment environment;

    @Autowired
    private IStudentService studentService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("list", studentService.findAll());
        model.addAttribute("mess", "done edit");
        return "list";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable Long id){
        ModelAndView mav = new ModelAndView("/edit");
        mav.addObject("student", studentService.findById(id));
        return mav;
    }

    @PostMapping("/edit/{id}")
    public String editStudent(@ModelAttribute Student student, Model model){
        model.addAttribute("student", student);
        studentService.update(student);
        return "redirect:/";
    }

    @GetMapping("/create")
    public ModelAndView showFormCreate(){
        ModelAndView modelAndView = new ModelAndView("/student/create");
        modelAndView.addObject("student", new StudentForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createStudent(@ModelAttribute StudentForm studentForm){
        //1 gan student nhung thuoc tinh cua studentForm
        Student student = new Student(studentForm.getName(), studentForm.getAddress());
        MultipartFile file = studentForm.getImage();
        String image = file.getOriginalFilename();
        student.setImage(image);
        String fileUpload = environment.getProperty("file_upload").toString();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + image));
        } catch (IOException e) {
            e.printStackTrace();
        }

        studentService.save(student);
        return new ModelAndView("/student/create", "student", new StudentForm());

    }
}
