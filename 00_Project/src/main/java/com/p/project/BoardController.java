package com.p.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.p.project.model.BoardVO;
import com.p.project.model.Criteria;
import com.p.project.model.PageMaker;
import com.p.project.service.BoardService;
import com.p.project.test.BoardDAOTest;

//Controller �帧����
//�Խ��� ���, �۾���, �󼼺���(��ȸ�� ����ó��), ����, ����
@Controller //���� Ŭ������ ��Ʈ�ѷ� bean���� ���
@RequestMapping("/board/*")
public class BoardController {
	
	private static final Logger logger=LoggerFactory.getLogger(BoardController.class);
	
	//�������� ���� ->BoardServiceImpl ����
	//IoC �������� ����
	//'@Autowired'�� '@Inject'�ּ��� �����ϰ� �۵��Ѵٰ� �Ǵ��߽��ϴ�. �� �� �ּ� ��� 'AutowiredAnnotationBeanPostProcessor'�� ����Ͽ� ���Ӽ��� �����մϴ�. 
	//'@Autowired'�� '@Inject'�� Spring bean�� �����ϱ� ���� ��ȣ ��ȯ ������ ���� �� �ֽ��ϴ�. 
	@Inject
	BoardService boardService;
	
	//01.�Խñ� ���
	@RequestMapping("list.do")
	// @RequestParam(defaultValue="") == �⺻�� �Ҵ�
	public ModelAndView list(@RequestParam(defaultValue="title") String searchOption, @RequestParam(defaultValue="") String keyword) throws Exception{
		List<BoardVO> list=boardService.listAll(searchOption, keyword);	
		//���ڵ��� ����
		int count=boardService.countArticle(searchOption, keyword);
		ModelAndView mav=new ModelAndView();
		//�����͸� �ʿ� ����
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("list", list);
		map.put("count", count); //���ڵ� ����
		map.put("searchOption", searchOption);//�˻� �ɼ�
		map.put("keyword", keyword);//�˻� Ű����
		mav.setViewName("board/list");//�並 list.jsp�� ����
		mav.addObject("map", map);//�ʿ� ����� �����͸� mav�� ����
		//System.out.println("list.toString()");
		return mav;
	}
	
	//list with page
	@RequestMapping(value="listCri", method=RequestMethod.GET)
	public void listAll(Criteria cri, Model model) throws Exception{
		logger.info("show list page with Criteria ......");
		model.addAttribute("list", boardService.listCriteria(cri));
	}
	
	@RequestMapping(value="listPage", method=RequestMethod.GET)
	public void listPage(@ModelAttribute("cri")Criteria cri, Model model) throws Exception{
		logger.info(cri.toString());
		
		model.addAttribute("list", boardService.listCriteria(cri));
		PageMaker pageMaker=new PageMaker();
		pageMaker.setCri(cri);
		//pageMaker.setTotalCount(131); sample paging
		pageMaker.setTotalCount(boardService.listCountCriteria(cri));
		model.addAttribute("pageMaker", pageMaker);
	}
	
	//02_1 �Խñ� �ۼ�ȭ��
	//@RequestMapping("board/write.do")
	//value="", method="���۹��"
	@RequestMapping(value="write.do", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	//02_02 �Խñ� �ۼ� ó��
	@RequestMapping(value="insert.do",method=RequestMethod.POST)
	public String insert(@ModelAttribute BoardVO vo)throws Exception{
		boardService.create(vo);
		return "redirect:list.do";
	}
	
	//03 �Խñ� �󼼳��� ��ȸ, �Խñ� ��ȸ�� ���� ó��
	//@RequestParam:get/post ������� ���޵� ���� 1��
	//HttpSession ���� ��ü
	@RequestMapping(value="view.do",method=RequestMethod.GET)
	public String view(@RequestParam int bno, HttpSession session, Model model)throws Exception{
		//��ȸ�� ���� ó��
		boardService.increaseViewcnt(bno, session);
		
		/*ModelAndView mav=new ModelAndView(); //��(������)+��(ȭ��)�� �Բ� �����ϴ� ��ü
		mav.setViewName("board/view"); //���� �̸�
		mav.addObject("dto",boardService.read(bno)); //�信 ������ ������*/
		
		model.addAttribute("dto", boardService.read(bno));
		return "board/view";
	}
	
	//04 �Խñ� ����
	//������ �Է��� ������� @ModelAttribute BoardDTO dto�� ���޵�
	@RequestMapping(value="updateGet.do")
	public String updateGet(int bno, Model model) throws Exception {
		model.addAttribute("dto", boardService.read(bno));
		return "board/update";
	}
	
	@RequestMapping(value="updatePost",method=RequestMethod.POST)
	public String update(@ModelAttribute BoardVO vo)throws Exception{
		boardService.update(vo);
		return "redirect:list.do";
	}
	
	//05 �Խñ� ����
	@RequestMapping("delete.do")
	public String delete(@RequestParam int bno)throws Exception{
		boardService.delete(bno);
		return "redirect:list.do";
	}
}//BoardController