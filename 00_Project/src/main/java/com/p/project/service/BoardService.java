package com.p.project.service;

import java.util.List;
import javax.servlet.http.HttpSession;
import com.p.project.model.BoardVO;
import com.p.project.model.Criteria;
import com.p.project.persistence.BoardDAO;;

//����Ͻ� ����. �䱸������ �޼ҵ�� �����ؼ� �������̽� ����
//Service ����Ͻ� ����, DB���� �̿��� �۾�
//�������̽�
public interface BoardService {
	//01 �Խñ� �ۼ�
	public void create(BoardVO vo) throws Exception;
	//02 �Խñ� �󼼺���
	public BoardVO read(int bno) throws Exception;
	//03 �Խñ� ����
	public void update(BoardVO dto) throws Exception;
	//04 �Խñ� ����
	public void delete(int bno) throws Exception;
	//05 �Խñ� ��ü ��� --> �˻� �ɼ�, Ű���� �Ű����� �߰�
	public List<BoardVO> listAll(String searchOption, String keyword) throws Exception;
	//06 �Խñ� ��ȸ
	public void increaseViewcnt(int bno, HttpSession session)throws Exception;
	//07 �Խñ� ���ڵ� ���� �޼ҵ� �߰�
	public int countArticle(String searchOption, String keyword) throws Exception;
	//����¡ ó�� �޼ҵ� �߰�
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	//���� page�� count
	public int listCountCriteria(Criteria cri) throws Exception;
}