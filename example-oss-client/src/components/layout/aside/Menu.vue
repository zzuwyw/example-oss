<script setup>

import {HomeFilled, Setting, UserFilled, Menu} from "@element-plus/icons-vue";
import useMenuStore from "@/stores/menu.js";

const menuStore = useMenuStore();
</script>

<template>
  <el-scrollbar>
    <!-- :unique-opened="true" 子菜单不能同时展开 -->
    <el-menu :collapse-transition="false" :router="false"
             :collapse="menuStore.isCollapse"
             :unique-opened="true"
             default-active="1"
             background-color="#33a06f"
             active-text-color="black"
             text-color="white">
      <el-menu-item index="1">
        <el-icon><home-filled /></el-icon>
        <span>首页</span>
      </el-menu-item>
      <el-sub-menu index="2">
        <template #title>
          <el-icon><Setting /></el-icon>
          <span>系统管理</span>
        </template>
        <el-menu-item index="1-4-1">
          <el-icon><UserFilled /></el-icon>
          <span>用户管理</span>
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
      <el-sub-menu index="5">
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
      <el-sub-menu index="6">
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
    </el-menu>
  </el-scrollbar>
</template>

<style>

</style>

<style scoped>

</style>