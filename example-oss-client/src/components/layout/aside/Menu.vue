<script setup>

import {HomeFilled, Setting, UserFilled, Menu} from "@element-plus/icons-vue";
import useMenuStore from "@/stores/menu.js";

const menuStore = useMenuStore();
</script>

<template>
  <!-- :unique-opened="true" 子菜单不能同时展开 -->
  <el-menu :collapse-transition="true" :router="true"
           :collapse="menuStore.isCollapse"
           :unique-opened="true"
           default-active="/home"
           background-color=transparent
           active-text-color="black"
           text-color="rgba(0, 0, 0, 0.65)">
    <el-menu-item index="/home">
      <template #title>
        <el-icon><home-filled /></el-icon>
        <span>首页</span>
      </template>
    </el-menu-item>
    <el-sub-menu index="/home22">
      <template #title>
        <el-icon><Setting /></el-icon>
        <span>系统管理</span>
      </template>
      <el-menu-item index="/user">
        <span style="padding-left: 10px">用户管理</span>
      </el-menu-item>
    </el-sub-menu>
    <el-sub-menu index="3">
      <template #title>
        <el-icon><Menu /></el-icon>
        <span>业务管理</span>
      </template>
      <el-menu-item index="2-2">
        <el-icon><UserFilled /></el-icon>
        <span>商品管理</span>
      </el-menu-item>
      <el-menu-item index="2-3">
        <el-icon><UserFilled /></el-icon>
        <span>订单管理</span>
      </el-menu-item>
    </el-sub-menu>
    <el-sub-menu index="4">
      <template #title>
        <el-icon><Menu /></el-icon>
        <span>列表页</span>
      </template>
      <el-sub-menu index="4-1">
        <template #title>
          <el-icon><Menu /></el-icon>
          <span>列表页2</span>
        </template>
        <el-menu-item index="4-2">
          <span style="padding-left: 10px">商品管理</span>
        </el-menu-item>
        <el-menu-item index="4-3">
          <span style="padding-left: 10px">订单管理</span>
        </el-menu-item>

      </el-sub-menu>
    </el-sub-menu>
  </el-menu>
</template>

<style>

</style>

<style scoped>

</style>