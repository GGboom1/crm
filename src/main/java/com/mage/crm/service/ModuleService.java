package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.ModuleDao;
import com.mage.crm.dao.PermissionDao;
import com.mage.crm.dto.ModuleDto;
import com.mage.crm.query.ModuleQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.Module;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService {
    @Resource
    private ModuleDao moduleDao;
    @Resource
    private PermissionDao permissionDao;


    public List<ModuleDto> queryAllModuleDtos(Integer rid) {
        List<ModuleDto> moduleDtoList = moduleDao.queryAllModuleDtos();
        List<Integer> moduleIds = permissionDao.queryAllModuleIdsByRid(rid);
        if (moduleIds != null && moduleIds.size() > 0){
            for(ModuleDto moduleDto : moduleDtoList){
                Integer moduleId = moduleDto.getId();
                for (Integer id : moduleIds){
                    if(moduleId == id){
                        moduleDto.setChecked(true);
                    }
                }
            }
        }
        return moduleDtoList;
    }

    public Map<String, Object> queryModulesByParams(ModuleQuery moduleQuery) {
        PageHelper.startPage(moduleQuery.getPage(), moduleQuery.getRows());
        List<Module> moduleList = moduleDao.queryModulesByParams(moduleQuery);
        PageInfo<Module> modulePageInfo = new PageInfo<>(moduleList);
        Map<String, Object> map = new HashMap<>();
        map.put("total", modulePageInfo.getTotal());
        map.put("rows", modulePageInfo.getList());
        return map;
    }

    public List<Module> queryModulesByGrade(Integer grade) {
        return moduleDao.queryModulesByGrade(grade);
    }

    public void insert(Module module) {
        checkParams(module);
        Integer grade = module.getGrade();
        AssertUtil.isTrue(null != moduleDao.queryModuleByOptValue(module.getOptValue()), "权限值不能重复!");
        AssertUtil.isTrue(null != moduleDao.queryModuleByGradeAndModuleName(module.getGrade(), module.getModuleName()),
                "该层级下模块名已存在!");
        if (module.getGrade() != 0) {
            AssertUtil.isTrue(null == moduleDao.queryModuleByPid(module.getParentId()), "父级菜单不存在!");
        }
        module.setIsValid(1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleDao.insert(module) < 1,"添加数据失败");
    }

    private void checkParams(Module module){
        AssertUtil.isTrue(module == null, "数据不能为空");
        AssertUtil.isTrue(module.getModuleName() == null || "".equals(module.getModuleName()), "模块名称不能为空");
        AssertUtil.isTrue(module.getOptValue() == null || "".equals(module.getOptValue()), "权限值不能为空");
        AssertUtil.isTrue(module.getGrade() == null, "层级不能为空");
        AssertUtil.isTrue(module.getParentId() == null, "上级不能为空");
    }

    public void update(Module module) {
        checkParams(module);
        AssertUtil.isTrue(null != moduleDao.queryModuleByOptValue(module.getOptValue()), "权限值不能重复!");
        AssertUtil.isTrue(null != moduleDao.queryModuleByGradeAndModuleName(module.getGrade(), module.getModuleName()),
                "该层级下模块名已存在!");
        if (module.getGrade() != 0) {
            AssertUtil.isTrue(null == moduleDao.queryModuleByPid(module.getParentId()), "父级菜单不存在!");
        }
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleDao.update(module) < 1,"添加数据失败");
    }

    public void delete(Integer[] ids) {
        AssertUtil.isTrue(ids == null || ids.length < 1, "请选中一条数据");
        AssertUtil.isTrue(moduleDao.delete(ids) < ids.length,"删除数据失败");
    }
}
