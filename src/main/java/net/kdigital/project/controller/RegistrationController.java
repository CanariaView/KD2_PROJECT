package net.kdigital.project.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.kdigital.project.domain.Member;
import net.kdigital.project.domain.Note;
import net.kdigital.project.service.EmailService;
import net.kdigital.project.service.RegistrationService;

@Controller
public class RegistrationController {

    @Autowired
    EmailService emailService;
    
    @Autowired
    RegistrationService registrationService;  
    
    @Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/signin")
	public String signin() {
		
		return "/signin";
	}
	
	@PostMapping("/idCheck")
	@ResponseBody
	public String idCheck(String memberid) {
		Member m = registrationService.idCheck(memberid);
		
		if(m == null) return "success";
		return "fail";
	}


    @PostMapping("/signup")
    public String register(Member member) {
    	
        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;
    	
    	member.setImageseq(randomNumber);
    	
    	int result = registrationService.regist(member);
        emailService.sendWelcomeEmail(member.getEmail());
        
        new ModelAndView("registrationSuccess");

        return "redirect:/";
    }
    
    @PostMapping("/note")
    @ResponseBody
    public void note(Note note) {
    	
    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = now.format(formatter);
    	
    	note.setRegdate(formattedDateTime);
    	
        emailService.sendNoteEmail(note);
        
        new ModelAndView("registrationSuccess");

    }
    
}