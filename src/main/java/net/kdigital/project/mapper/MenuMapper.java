package net.kdigital.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.kdigital.project.domain.Member;
import net.kdigital.project.domain.Reply;

@Mapper
public interface MenuMapper {
	
	public String getEmail(String memberid);

	public List<Reply> selectReplyAll();

	public int insertReply(Reply reply);

	public int deleteReply(String replyseq);

	public int getImageseq(String memberid);
	
}
