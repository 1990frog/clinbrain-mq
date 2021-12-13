package com.clinbrain.mq.controller.admin;


import com.clinbrain.mq.common.base.BaseController;
import com.clinbrain.mq.common.domain.AjaxResult;
import com.clinbrain.mq.common.domain.ResultTable;
import com.clinbrain.mq.model.custom.Tablepar;
import com.clinbrain.mq.model.custom.UContact;
import com.clinbrain.mq.model.custom.UContactDetails;
import com.clinbrain.mq.service.UContactService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller
 * @ClassName: UContactController
 * @author fuce
 * @date 2021-12-09 13:37:41
 */
@Api(value = "")
@Controller
@RequestMapping("/UContactController")
public class UContactController extends BaseController {
	
	private String prefix = "admin/uContact";
	
	@Autowired
	private UContactService uContactService;
	
	
	/**
	 * 页面展示
	 * @param model
	 * @return String
	 * @author fuce
	 */
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/view")
	@RequiresPermissions("gen:uContact:view")
    public String view(ModelMap model)
    {
        return prefix + "/list";
    }
	
	/**
	 * list集合
	 * @param tablepar
	 * @param uContact
	 * @return
	 */
	//@Log(title = "", action = "111")
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/list")
	@RequiresPermissions("gen:uContact:list")
	@ResponseBody
	public ResultTable list(Tablepar tablepar, UContact uContact){
		PageInfo<UContact> page=uContactService.list(tablepar,uContact);
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
	@RequiresPermissions("gen:uContact:add")
	@ResponseBody
	public AjaxResult add(UContact uContact){
		int b=uContactService.insertSelective(uContact);
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
	@RequiresPermissions("gen:uContact:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=uContactService.deleteByPrimaryKey(ids);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	
	/**
	 * 修改跳转
	 * @param id
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "修改跳转", notes = "修改跳转")
	@GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap map)
    {
        map.put("UContact", uContactService.selectByPrimaryKey(id));

        return prefix + "/edit";
    }
	
	/**
     * 修改保存
     */
    //@Log(title = "修改", action = "111")
	@ApiOperation(value = "修改保存", notes = "修改保存")
    @RequiresPermissions("gen:uContact:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UContact uContact)
    {
        return toAjax(uContactService.updateByPrimaryKeySelective(uContact));
    }

    @ApiOperation(value = "编辑联系方式", notes = "联系人联系方式修改")
    @GetMapping("/contact/{contactId}")
    public String concatEdit(@PathVariable("contactId") Integer id, ModelMap map) {
        //先根据联系人ID查找所有的联系方式
        List<UContactDetails> contacts = uContactService.selectContactDetailByConcatId(id);
        map.put("contacts", contacts);
        map.put("contactId", id);
        return prefix + "/detail";
    }

    @ApiOperation(value = "编辑联系方式", notes = "联系人联系方式修改")
    @RequiresPermissions("gen:uContact:edit")
    @PostMapping("/contact/{contactId}")
    @ResponseBody
    public AjaxResult contactEdit(@PathVariable("contactId") Integer id, @RequestBody List<UContactDetails> details) {
        //先根据联系人ID查找所有的联系方式
        if(!details.isEmpty()) {
            final List<UContactDetails> detailsList = details.stream().peek(d -> d.setContactId(id)).collect(Collectors.toList());
            long save = uContactService.saveContactDetails(detailsList);
            return AjaxResult.successData(save > 0);
        }
        return error();
    }

    @ApiOperation(value = "编辑联系方式", notes = "联系人联系方式删除")
    @RequiresPermissions("gen:uContact:remove")
    @DeleteMapping("/contactDetail/{id}")
    @ResponseBody
    public AjaxResult contactDetailDelete(@PathVariable("id") Integer id) {

        long b = uContactService.deleteContactDetailById(id);
        if(b>0){
            return success();
        }else{
            return error();
        }
    }


}
