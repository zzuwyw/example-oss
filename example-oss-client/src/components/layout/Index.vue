<script setup>
import Header from "@/components/layout/header/Index.vue";
import Aside from "@/components/layout/aside/Index.vue";
import Main from "@/components/layout/Main.vue";
import useMenuStore from "@/stores/menu.js";

const menuStore = useMenuStore();
</script>

<template>
  <el-container class="layout-container">
    <el-aside class="layout-aside transition-all" :style="{width: !menuStore.isCollapse ? menuStore.menuWidth + 'px' : '65px'}">
      <Aside />
    </el-aside>
    <el-container>
      <el-header><Header /></el-header>
      <el-container class="layout-main">
        <Main></Main>
      </el-container>
    </el-container>
  </el-container>
</template>

<style>
@import "@/scss/global-cover-element.scss";
</style>

<style lang="scss" scoped>
.layout-container {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  .layout-aside {
    z-index: 3; // 左侧菜单层级
    background-color: var(--el-menu-bg-color);
    border-right: none;
  }
  .layout-header {
    height: 52px;
    background-color: var(--el-header-bg-color);
  }
  .layout-main {
    box-sizing: border-box;
    padding: 0;
    overflow-x: hidden;
    background-color: var(--el-bg-color);
  }
}
</style>