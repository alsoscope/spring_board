package com.p.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.p.project.model.Criteria;
import com.p.project.model.PageMaker;
import com.p.project.model.ReplyVO;
import com.p.project.service.ReplyService;

//스프링 버전 3이므로 RestControlelr X, ResponseBody 어노테이션 사용
@Controller
@RequestMapping("/replies")
public class ReplyController {

	@Inject
	private ReplyService service;
	
	//1. JSON타입으로 데이터 전송. 댓글등록
	//ResponseBody annotation : return되는 값은 view가 아닌 HTTP ResponseBody에 직접 쓰여지게 된다
	@ResponseBody
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO vo){
		ResponseEntity<String> entity=null;
		
		try {
			service.addReply(vo);
			entity=new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}//register()--------------------------
	
	//2. 전체 댓글 목록 처리
	@ResponseBody
	@RequestMapping(value="/all/{bno}", method=RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") int bno){
		ResponseEntity<List<ReplyVO>> entity=null;
		
		try {
			entity=new ResponseEntity<>(service.listReply(bno), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}//list()-------------------------------
	
	//3. 수정처리 REST방식에서 update 작업은 PUT,PATCH방식으로 처리. 전체 데이터 수정은 PUT, 일부 데이터 수정은 PATCH 
	@ResponseBody
	@RequestMapping(value="/{rno}", method= {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("rno") int rno, @RequestBody ReplyVO vo){
		ResponseEntity<String> entity=null;
		
		try{
			vo.setRno(rno);
			service.modifyReply(vo);
			
			entity=new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}//update()---------------------
	
	//4. 삭제. 삭제처리는 PUT과 유사하지만 추가적인 데이터가 없다
	@ResponseBody
	@RequestMapping(value="/{rno}", method=RequestMethod.DELETE)
	public ResponseEntity<String> remove(@PathVariable("rno") int rno){
		ResponseEntity<String> entity=null;
		
		try {
			service.removeReply(rno);
			entity=new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST );
		}
		return entity;
	}//remove() -------------------
	
	//5.페이징 처리. 두 개의 @PathVariable 이용해서 처리
	@ResponseBody
	@RequestMapping(value="/{bno}/{page}", method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> listPage(@PathVariable("bno")int bno, @PathVariable("page") int page){
		ResponseEntity<Map<String, Object>> entity=null;
		
		try {
			Criteria cri=new Criteria();
			cri.setPage(page);
			
			PageMaker pageMaker=new PageMaker();
			pageMaker.setCri(cri);
			
			Map<String, Object> map=new HashMap<String, Object>();
			List<ReplyVO> list=service.listReplyPage(bno, cri);
			
			map.put("list", list);
			
			int replyCount=service.count(bno);
			pageMaker.setTotalCount(replyCount);
			
			map.put("pageMaker", pageMaker);
			
			entity=new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<Map<String,Object>> (HttpStatus.BAD_REQUEST);
		}  
		return entity;
	}//listPage()-------------------

}//ReplyController
