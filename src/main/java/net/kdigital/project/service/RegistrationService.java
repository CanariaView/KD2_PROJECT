package net.kdigital.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.kdigital.project.domain.Member;
import net.kdigital.project.mapper.RegistrationMapper;
import oracle.jdbc.proxy.annotation.Post;

@Service
public class RegistrationService {
	
	@Autowired
	RegistrationMapper mapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public int regist(Member member) {
		
		// 비밀번호 암호화 이후 비밀번호 재설정
		String encodedPassword = passwordEncoder.encode(member.getMemberpwd());
		member.setMemberpwd(encodedPassword);
		
		int result = mapper.regist(member);
		
		return result;
		
	}

	public Member idCheck(String memberid) {
		
		Member m = mapper.idCheck(memberid);
		
		return m;
	}
	
	
	

}
