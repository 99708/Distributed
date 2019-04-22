package org.xyq.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xyq.basic.util.EnumUtils;
import org.xyq.basic.util.JsonUtil;
import org.xyq.cms.auth.AuthClass;
import org.xyq.cms.dto.AjaxObj;
import org.xyq.cms.dto.TreeDto;
import org.xyq.cms.model.Channel;
import org.xyq.cms.model.ChannelTree;
import org.xyq.cms.model.ChannelType;
import org.xyq.cms.service.IChannelService;


@RequestMapping("/admin/channel")
@Controller
@AuthClass
public class ChannelController {
	
	private IChannelService channelService;
	
	public IChannelService getChannelService() {
		return channelService;
	}
	
	@Inject
	public void setChannelService(IChannelService channelService) {
		this.channelService = channelService;
	}
	
	@RequestMapping("/channels")
	public String list(Model model){
		model.addAttribute("treeDatas", JsonUtil.getInstance().obj2json(channelService.generateTree()));
		return "channel/list";
		
	}
	@RequestMapping("/treeAll")
	public @ResponseBody List<ChannelTree> tree(){
		return channelService.generateTree();
	}
	@RequestMapping(value="/treeAs",method=RequestMethod.POST)
	public @ResponseBody List<TreeDto> tree(@Param Integer pid) {
		List<TreeDto> tds = new ArrayList<TreeDto>();
		if(pid==null || pid <= 0) {
			tds.add(new TreeDto(0,"网站根栏目",1));
			return tds;
		}
		List<ChannelTree> cts = channelService.generateTreeByParent(pid);
		for(ChannelTree ct:cts) {
			tds.add(new TreeDto(ct.getId(),ct.getName(),1));
		}
		return tds;
	}
	@RequestMapping("/channels/{pid}")
	public String listChild(@PathVariable Integer pid,@Param Integer refresh,  Model model){
		Channel pc = null;
		if(refresh == null){
			model.addAttribute("refresh", 0);
		}else{
			model.addAttribute("refresh", 1);
		}
		if (pid == null || pid ==0){
			pc = new Channel();
			pc.setName(Channel.ROOT_NAME);
			pc.setId(Channel.ROOT_ID);
		}else{
			pc = channelService.load(pid);
		}
		model.addAttribute("pc", pc);
		model.addAttribute("channels", channelService.listByParent(pid));
		return "channel/list_child";
		
	}
	private void initAdd(Model model, Integer pid){
		if(pid == null){
			pid =0;
		}
		Channel  pc = null;
		if(pid == 0){
			pc = new Channel();
			pc.setId(Channel.ROOT_ID);
			pc.setName(Channel.ROOT_NAME);
		}else{
			pc = channelService.load(pid);
		}
		model.addAttribute("pc", pc);
		model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
	}
	
	@RequestMapping(value="/add/{pid}", method = RequestMethod.GET)
	public String add(@PathVariable Integer pid, Model model){
		initAdd(model, pid);
		model.addAttribute(new Channel());
		return "channel/add";
		
	}
	@RequestMapping(value="/add/{pid}", method = RequestMethod.POST)
	public String add(@PathVariable Integer pid, Channel channel, BindingResult br, Model model){
		if(br.hasErrors()){
		initAdd(model, pid);
		return "channel/add";
		}
		channelService.add(channel, pid);
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	@RequestMapping("/delete/{pid}/{id}")
	public String delete(@PathVariable Integer pid, @PathVariable Integer id){
		channelService.delete(id);
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	
	@RequestMapping(value="/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable Integer id, Model model){
		Channel c = channelService.load(id);
		model.addAttribute("channel", c);
		Channel pc = null;
		if(c.getParent() == null){
			pc = new Channel();
			pc.setId(Channel.ROOT_ID);
			pc.setName(Channel.ROOT_NAME);
		}else{
			pc = c.getParent();
		}
		model.addAttribute("pc", pc);
		model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
		return "channel/update";
		
	}
	@RequestMapping(value="/update/{id}", method = RequestMethod.POST)
	public String update(@PathVariable Integer id, Channel channel, BindingResult br, Model model){
		if(br.hasErrors()){
			model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
			return "channel/update";
		}
		Channel tc = channelService.load(id);
		int pid = 0;
		if(tc.getParent() != null){
			pid = tc.getParent().getId();
		}
		tc.setCustomLink(channel.getCustomLink());
		tc.setCustomLinkUrl(channel.getCustomLinkUrl());
		tc.setIsCommend(channel.getIsCommend());
		tc.setIsIndex(channel.getIsIndex());
		tc.setIsStatus(channel.getIsStatus());
		tc.setIsTopNav(channel.getIsTopNav());
		tc.setName(channel.getName());
		tc.setType(channel.getType());
		channelService.update(tc);
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	@RequestMapping("/channels/updateSort")
	public @ResponseBody AjaxObj updateSort(@Param Integer[] ids){
		try {
			channelService.updateSort(ids);
		} catch (Exception e) {
			return new AjaxObj(0, e.getMessage());
		}
		return new AjaxObj(1);
	}
}

