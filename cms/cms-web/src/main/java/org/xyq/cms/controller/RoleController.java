package org.xyq.cms.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xyq.basic.util.EnumUtils;
import org.xyq.cms.auth.AuthClass;
import org.xyq.cms.model.Role;
import org.xyq.cms.model.RoleType;
import org.xyq.cms.service.IRoleService;
import org.xyq.cms.service.IUserService;

@RequestMapping("/admin/role")
@Controller
@AuthClass
public class RoleController {
	private IRoleService roleService;
	private IUserService userService;
	
	public IRoleService getRoleService() {
		return roleService;
	}
	@Inject
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public IUserService getUserService() {
		return userService;
	}
	@Inject
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	@RequestMapping("/roles")
	public String list(Model model){
		model.addAttribute("roles",roleService.listRole());
		return "role/list";
	}
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(Model model){
		model.addAttribute(new Role());
		model.addAttribute("types", EnumUtils.enum2Name(RoleType.class));
		return "role/add";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(Role role){
		roleService.add(role);
		return "redirect:/admin/role/roles";
	}
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String update(@PathVariable int id, Model model){
		model.addAttribute(roleService.load(id));
		model.addAttribute("types", EnumUtils.enum2Name(RoleType.class));
		return "role/update";
	}

	@RequestMapping(value="/update/{id}", method=RequestMethod.POST)
	public String update(@PathVariable int id, @Validated Role role, BindingResult br){
		if(br.hasErrors()){
			return "role/update";
		}
		Role r = roleService.load(id);
		r.setName(role.getName());
		r.setRoleType(role.getRoleType());
		roleService.update(r);
		return "redirect:/admin/role/roles";
	}
	@RequestMapping("/delete/{id}")
	public String delete (@PathVariable int id){
		roleService.delete(id);
		return "redirect:/admin/role/roles";
	}
	@RequestMapping("{id}")
	public String show(@PathVariable int id, Model model){
		model.addAttribute(roleService.load(id));
		model.addAttribute("us",userService.listRoleUsers(id));
		return "role/show";
	}
	@RequestMapping("/clearUsers/{id}")
	public String cleanRoleUsers(@PathVariable int id){
		roleService.deleteRoleUser(id);
		return "redirect:/admin/role/roles";
		
	}
}
