package net.kdigital.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import net.kdigital.project.domain.Note;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendWelcomeEmail(String recipientEmail) {
    	
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Welcome to Canaria View!");
        message.setText("환영합니다! 회원가입이 완료되었습니다.");

        javaMailSender.send(message);
    }

	public void sendNoteEmail(Note note) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(note.getEmail());
        message.setSubject("Thank you for your quotation!");
        message.setText("Thank you for your quotation.\n\n"
        				+ "Date: " + note.getRegdate() + "\n"
        				+ "Name: " + note.getName() + "\n"
        				+ "Phone: " + note.getPhone() + "\n"
        				+ "Message " + note.getText() + "\n\n"
        				+ "We will get back as soon as possible!");

        javaMailSender.send(message);
		
	}
}