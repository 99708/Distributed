package org.xyq.cms.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.xyq.basic.model.SystemContext;
import org.xyq.basic.util.JsonUtil;
import org.xyq.cms.auth.AuthClass;
import org.xyq.cms.auth.AuthMethod;
import org.xyq.cms.dto.AjaxObj;
import org.xyq.cms.dto.TopicDto;
import org.xyq.cms.model.Attachment;
import org.xyq.cms.model.ChannelTree;
import org.xyq.cms.model.Topic;
import org.xyq.cms.model.User;
import org.xyq.cms.service.IAttachmentService;
import org.xyq.cms.service.IChannelService;
import org.xyq.cms.service.IKeywordService;
import org.xyq.cms.service.ITopicService;

@Controller
@AuthClass("login")
@RequestMapping("/admin/topic")
public class TopicController {
	
	private final static List<String> imgTypes = Arrays.asList("jpg", "jpeg", "gif", "png");
	private ITopicService topicService;
	private IChannelService channelService;
	private IKeywordService keywordService;
	private IAttachmentService attachmentService;
	
	public ITopicService getTopicService() {
		return topicService;
	}
	
	@Inject
	public void setTopicService(ITopicService topicService) {
		this.topicService = topicService;
	}
	
	public IChannelService getChannelService() {
		return channelService;
	}
	@Inject
	public void setChannelService(IChannelService channelService) {
		this.channelService = channelService;
	}

	public IKeywordService getKeywordService() {
		return keywordService;
	}
	@Inject
	public void setKeywordService(IKeywordService keywordService) {
		this.keywordService = keywordService;
	}

	public IAttachmentService getAttachmentService() {
		return attachmentService;
	}
	@Inject
	public void setAttachmentService(IAttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	private void initList(String con, Integer cid, Integer status, Model model, HttpSession session){
		SystemContext.setSort("t.publishDate");
		SystemContext.setOrder("desc");
		boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
		if(isAdmin){
			model.addAttribute("datas", topicService.find(cid, con, status));
		}else{
			User loginUser = (User) session.getAttribute("loginUser");
			model.addAttribute("datas", topicService.find(loginUser.getId(), cid, con, status));
		}
		if(con =="") con = "";
		SystemContext.removeSort();
		SystemContext.removeOrder();
		model.addAttribute("con", con);
		model.addAttribute("cid", cid);
		model.addAttribute("cs", channelService.listPublishChannels());
	}
	
	@RequestMapping("/audits")
	@AuthMethod(role="ROLE_PUBLISH,ROLE_AUDIT")
	public String auditsList(@RequestParam(required=false) String con, @RequestParam(required=false) Integer cid, Model model, HttpSession session){
		initList(con, cid, 1, model, session);
		return "topic/audits";
	}
	
	@RequestMapping("/unaudits")
	@AuthMethod(role="ROLE_PUBLISH,ROLE_AUDIT")
	public String unauditsList(@RequestParam(required=false) String con, @RequestParam(required=false) Integer cid, Model model, HttpSession session){
		initList(con, cid, 0, model, session);
		return "topic/unaudits";
	}
	
	@RequestMapping("/changeStatus/{id}")
	@AuthMethod(role="ROLE_AUDIT")
	public String changeStatus (@PathVariable int id, Integer status){
		topicService.updateStatus(id);
		if(status == 0){
			return "redirect:/admin/topic/unaudits";
		}else{
			return "redirect:/admin/topic/audits";
		}
	}
	
	@RequestMapping("/delete/{id}")
	@AuthMethod(role="ROLE_PUBLISH")
	public String delete (@PathVariable int id, Integer status){
		topicService.delete(id);
		if(status == 0){
			return "redirect:/admin/topic/unaudits";
		}else{
			return "redirect:/admin/topic/audits";
		}
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	@AuthMethod(role="ROLE_PUBLISH")
	public String add(Model model ){
		Topic topic = new Topic();
		topic.setPublishDate(new Date());
		TopicDto td = new TopicDto(topic);
		model.addAttribute("topicDto", td);
		return "topic/add";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@Validated TopicDto topicDto, BindingResult br,String[] aks, Integer[] aids,  HttpSession session){
		if(br.hasErrors()){
			return "topic/add";
		}
		Topic topic = topicDto.getTopic();
		User loginUser = (User) session.getAttribute("loginUser");
		StringBuffer keys = new StringBuffer();
		if(aks != null){
			for(String k:aks){
				keys.append(k).append("|");
				keywordService.addOrUpdate(k);
			}
		}
		topic.setKeyword(keys.toString());
		topicService.add(topic, topicDto.getCid(), loginUser.getId(), aids);
		return "redirect:/jsp/common/addSuc.jsp";
	} 
	
	@RequestMapping("/{id}")
	public String show(@PathVariable int id, Model model){
		model.addAttribute(topicService.load(id));
		model.addAttribute("atts", attachmentService.listAttachByTopic(id));
		return "topic/show";
	}
	
	@RequestMapping(value="/searchKeyword")
	@AuthMethod(role="ROLE_PUBLISH")
	public @ResponseBody List<String> searchKeyword(String term){
		return keywordService.listStringByCon(term);
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)//返回的是json数据类型的值，而uploadify只能接收字符串
	public void upload(MultipartFile attach, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/plain;charset=utf-8");
		AjaxObj ao = null;
		try {
			Attachment att = new Attachment();
			String ext = FilenameUtils.getExtension(attach.getOriginalFilename());
			System.out.println(ext);
			att.setIsAttach(0);
			if(imgTypes.contains(ext)) att.setIsImg(1);
			else att.setIsImg(0);
			att.setIsIndexPic(0);
			att.setNewName(String.valueOf(new Date().getTime())+"."+ext);
			att.setOldName(FilenameUtils.getBaseName(attach.getOriginalFilename()));
			att.setSuffix(ext);
			att.setType(attach.getContentType());
			att.setTopic(null);
			att.setSize(attach.getSize());
			attachmentService.add(att, attach.getInputStream());
			ao = new AjaxObj(1,null,att);
		} catch (IOException e) {
			ao = new AjaxObj(0,e.getMessage());
		}
		resp.getWriter().write(JsonUtil.getInstance().obj2json(ao));
	}
	
	@RequestMapping("/treeAll")
	@AuthMethod(role="ROLE_PUBLISH")
	public @ResponseBody List<ChannelTree> tree() {
		return channelService.generateTree();
	}
}
