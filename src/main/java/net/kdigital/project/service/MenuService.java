package net.kdigital.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kdigital.project.domain.Reply;
import net.kdigital.project.mapper.MenuMapper;


@Service
public class MenuService {
	
	@Autowired
	MenuMapper mapper;

	public String getEmail(String memberid) {
		
		String email = mapper.getEmail(memberid);
		
		return email;
	}

	public List<Reply> selectReplyAll() {
			
		List<Reply> list = mapper.selectReplyAll();
		
		return list;
	}

	public int insertReply(Reply reply) {

		int result = mapper.insertReply(reply);
		
		return result;
	}

	public int deleteReply(String replyseq) {
		int result = mapper.deleteReply(replyseq);
		
		return result;
	}

	public int getImageseq(String memberid) {
		int result = mapper.getImageseq(memberid);
		return result;
	}
	
	
	
	

}
