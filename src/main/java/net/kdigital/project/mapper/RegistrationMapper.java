package net.kdigital.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.kdigital.project.domain.Member;
import net.kdigital.project.domain.Reply;

@Mapper
public interface RegistrationMapper {

	public int regist(Member member);

	public Member idCheck(String memberid);
	
	
}
