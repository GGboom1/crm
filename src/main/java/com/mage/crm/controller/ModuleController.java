package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.dto.ModuleDto;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.ModuleQuery;
import com.mage.crm.service.ModuleService;
import com.mage.crm.vo.Module;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    @ResponseBody
    @RequestMapping("/queryAllModuleDtos")
    public List<ModuleDto> queryAllModuleDtos(Integer rid){
        return moduleService.queryAllModuleDtos(rid);
    }

    @RequestMapping("/index")
    public String index(){
        return "module";
    }

    @ResponseBody
    @RequestMapping("/queryModulesByParams")
    public Map<String, Object> queryModulesByParams(ModuleQuery moduleQuery){
        return moduleService.queryModulesByParams(moduleQuery);
    }

    @ResponseBody
    @RequestMapping("/queryModulesByGrade")
    public List<Module> queryModulesByGrade(Integer grade){
        return moduleService.queryModulesByGrade(grade);
    }

    @ResponseBody
    @RequestMapping("/insert")
    public MessageModel insert(Module module){
        moduleService.insert(module);
        return createMessageModel("添加数据成功");
    }

    @ResponseBody
    @RequestMapping("/update")
    public MessageModel update(Module module){
        moduleService.update(module);
        return createMessageModel("修改数据成功");
    }

    @ResponseBody
    @RequestMapping("/delete")
    public MessageModel delete(Integer[] ids){
        moduleService.delete(ids);
        return createMessageModel("删除数据成功");
    }
}
