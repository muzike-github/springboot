package com.example.myvueproject.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myvueproject.common.Result;
import com.example.myvueproject.entity.User;
import com.example.myvueproject.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;
    @PutMapping
    public Result<?> update(@RequestBody User user){
        //利用userMapper接口更新数据
        userMapper.updateById(user);
        return Result.success();

    }
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        userMapper.deleteById(id);
        return Result.success();
    }
    @PostMapping
    //@RequestBody将前台穿过来的json数据转为封装为对象
    public Result<?> save(@RequestBody User user){
        //利用userMapper接口插入数据
        userMapper.insert(user);
        return Result.success();

    }
    @GetMapping
    public Result<?> findAll(@RequestParam(defaultValue = "1")  Integer pageNum,//第几页
                             @RequestParam(defaultValue = "10") Integer pageSize,//规定每页显示的条数
                             @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        //isBlank()方法：判断字符串是否为null、""、空字符串，三种情况一起判断（需引入hutool依赖）
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getUsername,search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(userPage);
    }
}
