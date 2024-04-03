<script setup>
import Aside from "@/components/layout/aside/Index.vue";
import Header from "@/components/layout/header/Index.vue";
import Main from "@/components/layout/Main.vue";
import useMenuStore from "@/stores/menu.js";

const menuStore = useMenuStore();

</script>

<template>
  <div class="bg-figure">
    <img src='../../assets/images/bg-figure.webp' style="position: absolute; bottom: 0; left: 0; width: 331px;" alt="">
  </div>
  <el-container class="layout-container">
    <el-header><Header /></el-header>
    <el-container>
      <el-aside class="layout-aside transition-all" :style="{width: !menuStore.isCollapse ? menuStore.menuWidth + 'px' : '65px'}">
        <Aside />
      </el-aside>
      <el-container class="layout-main">
        <Main class="main-content"></Main>
      </el-container>
    </el-container>
  </el-container>
</template>

<style>
@import "@/scss/global-cover-element.scss";
</style>

<style lang="scss" scoped>
.bg-figure {
  pointer-events: none;
  position: fixed;
  overflow: hidden;
  inset-block-start: 0;
  inset-inline-start: 0;
  z-index: 0;
  height: 100%;
  width: 100%;
}
.layout-container {
  z-index: 1;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background: linear-gradient(#ffffff, #f3f3f3 28%);

  .layout-aside {
    border-right: none;
    background-color: transparent;
  }
  .layout-header {
    height: 52px;
    background-color: transparent;
  }
  .layout-main {
    height: calc(100vh - 44px);
    box-sizing: border-box;
    z-index: 2;
    padding: 0;
    overflow-x: hidden;
    background-color: transparent;
    .main-content {
      width: 100%;
      color: rgba(0, 0, 0, 0.88);
      font-size: var(--el-font-size-base);
    }
  }
}
</style>