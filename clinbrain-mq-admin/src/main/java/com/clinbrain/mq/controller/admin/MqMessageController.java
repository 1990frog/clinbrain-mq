package com.clinbrain.mq.controller.admin;

import com.clinbrain.mq.common.base.BaseController;
import com.clinbrain.mq.common.domain.AjaxResult;
import com.clinbrain.mq.common.domain.ResultTable;
import com.clinbrain.mq.model.custom.Tablepar;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.clinbrain.mq.service.MqMessageService;
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
 * Controller
 * @ClassName: MqMessageController
 * @author clinbrain
 * @date 2021-12-09 14:27:38
 */
@Api(value = "")
@Controller
@RequestMapping("/MqMessageController")
public class MqMessageController extends BaseController{
	
	private String prefix = "admin/mqMessage";
	
	@Autowired
	private MqMessageService mqMessageService;
	
	
	/**
	 * 页面展示
	 * @param model
	 * @return String
	 * @author clinbrain
	 */
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/view")
	@RequiresPermissions("gen:UMqMessage:view")
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
	//@Log(title = "", action = "111")
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/list")
	@RequiresPermissions("gen:UMqMessage:list")
	@ResponseBody
	public ResultTable list(Tablepar tablepar, UMqMessage UMqMessage){
		PageInfo<UMqMessage> page=mqMessageService.list(tablepar,UMqMessage) ; 
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
	//@Log(title = "新增", action = "111")
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/add")
	@RequiresPermissions("gen:UMqMessage:add")
	@ResponseBody
	public AjaxResult add(UMqMessage UMqMessage){
		int b=mqMessageService.insertSelective(UMqMessage);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	//@Log(title = "删除", action = "111")
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping("/remove")
	@RequiresPermissions("gen:UMqMessage:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=mqMessageService.deleteByPrimaryKey(ids);
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
        map.put("UMqMessage", mqMessageService.selectByPrimaryKey(id));

        return prefix + "/edit";
    }
	
	/**
     * 修改保存
     */
    //@Log(title = "修改", action = "111")
	@ApiOperation(value = "修改保存", notes = "修改保存")
    @RequiresPermissions("gen:UMqMessage:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UMqMessage UMqMessage)
    {
        return toAjax(mqMessageService.updateByPrimaryKeySelective(UMqMessage));
    }

    
    

	
}
