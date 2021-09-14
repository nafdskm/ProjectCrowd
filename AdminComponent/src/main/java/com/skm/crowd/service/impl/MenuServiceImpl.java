package com.skm.crowd.service.impl;

import com.skm.crowd.constant.CrowdConstant;
import com.skm.crowd.entity.Menu;
import com.skm.crowd.entity.MenuExample;
import com.skm.crowd.mapper.MenuMapper;
import com.skm.crowd.service.MenuService;
import com.skm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public ResultEntity<String> removeMenu(Integer id) {
        int result = menuMapper.deleteByPrimaryKey(id);
        if (result == 1) {
            return ResultEntity.successWithData(CrowdConstant.MESSAGE_DELETE_SUCCESS);
        }
        return ResultEntity.failed(CrowdConstant.MESSAGE_DELETE_FAILED);
    }
}
