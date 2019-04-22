package org.xyq.cms.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.xyq.basic.model.Pager;
import org.xyq.cms.dao.IKeywordDao;
import org.xyq.cms.model.Keyword;

@Service("keywordService")
public class KeywordService implements IKeywordService {
	
	private IKeywordDao keywordDao;
	

	public IKeywordDao getKeywordDao() {
		return keywordDao;
	}
	
	@Inject
	public void setKeywordDao(IKeywordDao keywordDao) {
		this.keywordDao = keywordDao;
	}

	@Override
	public void addOrUpdate(String name) {
		keywordDao.addOrUpdate(name);
	}

	@Override
	public List<Keyword> getKeywordByTimes(int num) {
		List<Keyword> ks = keywordDao.findUseKeyword();
		List<Keyword> kks = new ArrayList<Keyword>();
		for(Keyword k:ks){
			if (k.getTimes() >= num) {
				kks.add(k);
			} else {
				break;
			}
		}
		return kks;
	}

	@Override
	public List<Keyword> getMaxTimesKeyword(int num) {
		List<Keyword> ks = keywordDao.findUseKeyword();
		List<Keyword> kks = new ArrayList<Keyword>();
		if(ks.size() < num){
			return ks;
		}else {
			for (int i=0; i<num; i++) {
				kks.add(ks.get(i));
			}
		}
		return kks;
	}

	@Override
	public Pager<Keyword> findNoUseKeyword() {
		return keywordDao.findNoUseKeyword();
	}

	@Override
	public void clearNoUseKeyword() {
		keywordDao.clearNoUseKeyword();
	}

	@Override
	public List<Keyword> findUseKeyword() {
		return keywordDao.findUseKeyword();
	}

	@Override
	public List<Keyword> listKeywordByCon(String con) {
		return keywordDao.listKeywordByCon(con);
	}

	@Override
	public List<String> listStringByCon(String con) {
		return keywordDao.listStringByCon(con);
	}

}
