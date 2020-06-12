package scond.reply.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import secodn.reply.dao.ReplyDAO;
import second.reply.dto.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Inject
	ReplyDAO replyDAO;

	// 댓글 목록
	@Override
	public List<ReplyVO> list(int bno, int start, int end, HttpSession session){

	List<ReplyVO> items = replyDAO.list(bno, start, end);
	// 세션에서 현재 사용자 id값 저장
	String userId = (String)session.getAttribute("userId");for(
	ReplyVO vo:items)
	{
		// 댓글 목록중에 비밀 댓글이 있을경우
		if (vo.getSecretReply().equals("y")) {
			if (userId == null) { // 비로그인 상태면 비밀 댓글로 처리
				vo.setReplytext("비밀 댓글 입니다.");
			} else {// 로그인한 상태일 경우
				String writer = vo.getWriter(); // 게시글 작성자저장
				String replyer = vo.getReplyer(); // 댓글 작성자

				// 로그인한 사용자가 게시물의 작성자x, 댓글 작성자도x 비밀댓글 처리
				if (!userId.equals(writer) && !userId.equals(replyer)) {
					vo.setReplytext("비밀 댓글입니다.");
				}
			}
		}

	}
	return items;

}

//댓글 작성
@Override
public void create(ReplyVO vo) {
	replyDAO.create(vo);
}

//수정
@Override
public void update(ReplyVO vo) {
	
}

@Override
public void delete(Integer rno) {
	
}

//댓글 갯수
@Override
public int count(int bno) {
	return replyDAO.count(bno);
}
}