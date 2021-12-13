package com.clinbrain.mq.controller.admin;

import com.clinbrain.mq.common.base.BaseController;
import com.clinbrain.mq.common.domain.AjaxResult;
import com.clinbrain.mq.common.domain.ResultTable;
import com.clinbrain.mq.model.custom.Tablepar;
import com.clinbrain.mq.model.custom.sms.UMsgTemplate;
import com.clinbrain.mq.service.MsgTemplateService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 信息模板管理Controller
 * @ClassName: MsgTemplateController
 * @author clinbrain
 * @date 2021-12-09 14:28:10
 */
@Api(value = "信息模板管理")
@Controller
@RequestMapping("/MsgTemplateController")
public class MsgTemplateController extends BaseController{
	
	private String prefix = "admin/msgTemplate";
	
	@Autowired
	private MsgTemplateService msgTemplateService;
	
	
	/**
	 * 信息模板管理页面展示
	 * @param model
	 * @return String
	 * @author clinbrain
	 */
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/view")
	@RequiresPermissions("gen:UMsgTemplate:view")
    public String view(ModelMap model)
    {
        return prefix + "/list";
    }
	
	/**
	 * list集合
	 * @param tablepar
	 * @param searchText
	 * @return
	 */
	//@Log(title = "信息模板管理", action = "111")
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/list")
	@RequiresPermissions("gen:UMsgTemplate:list")
	@ResponseBody
	public ResultTable list(Tablepar tablepar, UMsgTemplate UMsgTemplate){
		PageInfo<UMsgTemplate> page=msgTemplateService.list(tablepar,UMsgTemplate) ; 
		return pageTable(page.getList(),page.getTotal());
	}
	
	/**
     * 新增跳转
     */
	@ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add(ModelMap modelMap)
    {
        return prefix + "/add";
    }
	
    /**
     * 新增保存
     * @param 
     * @return
     */
	//@Log(title = "信息模板管理新增", action = "111")
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/add")
	@RequiresPermissions("gen:UMsgTemplate:add")
	@ResponseBody
	public AjaxResult add(UMsgTemplate UMsgTemplate){
		int b=msgTemplateService.insertSelective(UMsgTemplate);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	/**
	 * 信息模板管理删除
	 * @param ids
	 * @return
	 */
	//@Log(title = "信息模板管理删除", action = "111")
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping("/remove")
	@RequiresPermissions("gen:UMsgTemplate:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=msgTemplateService.deleteByPrimaryKey(ids);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	
	/**
	 * 修改跳转
	 * @param id
	 * @param mmap
	 * @return
	 */
	@ApiOperation(value = "修改跳转", notes = "修改跳转")
	@GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap map)
    {
        map.put("msgTemplate", msgTemplateService.selectByPrimaryKey(id));

        return prefix + "/edit";
    }
	
	/**
     * 修改保存
     */
    //@Log(title = "信息模板管理修改", action = "111")
	@ApiOperation(value = "修改保存", notes = "修改保存")
    @RequiresPermissions("gen:UMsgTemplate:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UMsgTemplate UMsgTemplate)
    {
        return toAjax(msgTemplateService.updateByPrimaryKeySelective(UMsgTemplate));
    }

    
    

	
}
