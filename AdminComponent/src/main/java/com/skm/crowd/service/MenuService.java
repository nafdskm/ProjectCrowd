package com.skm.crowd.service;

import com.skm.crowd.entity.Menu;
import com.skm.crowd.util.ResultEntity;

import java.util.List;

public interface MenuService {

    List<Menu> getAll();

    void saveMenu(Menu menu);

    ResultEntity<String> removeMenu(Integer id);
}
