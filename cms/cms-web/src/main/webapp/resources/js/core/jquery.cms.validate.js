(function($){
	var __validate = $.fn.validate;
	$.fn.cmsvalidate = function(opts) {
		var __rules = $.extend({
			username:"required",
			password:"required",
			name:"required",
			confirmPwd:{
				equalTo:"#password"
			},
			email:"email",
			title:"required",
			cid:{
				min:1
			}
		},opts?(opts.rules||{}):{});
		var __messages = $.extend({
			username:"用户名不能为空",
			password:"用户密码不能为空",
			confirmPwd:"两次输入的密码不一致",
			email:"邮件格式不正确",
			name:"名称不能为空",
			title:"栏目必须填写文章标题",
			cid:"必须选择一个所属栏目"
		},opts?(opts.messages||{}):{});
		var __defaultOpts = $.extend(opts||{},{
			rules:__rules,
			messages:__messages,
			errorElement: opts?(opts.errorElement||"span"):"span",
			errorClass:opts?(opts.errorClass||"errorContainer"):"errorContainer"
		});
		$.extend($.fn.validate.prototype,__defaultOpts);
		__validate.call(this,__defaultOpts);
		return this;
	};
})(jQuery);
