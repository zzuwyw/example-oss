<script setup>
import Header from "@/components/layout/Header.vue";
import Aside from "@/components/layout/Aside.vue";
import Main from "@/components/layout/Main.vue";
import IconHeaderLogo from "@/components/icons/IconHeaderLogo.vue";
</script>

<template>
  <el-container>
    <el-header>

      <Header class="header"></Header>
    </el-header>
    <el-container>
      <el-aside
          class="aside transition-all"
      >
        <el-scrollbar>
          <!-- :unique-opened="true" 子菜单不能同时展开 -->
          <el-menu
              :collapse-transition="false"
              :router="false"
          >
            <Aside></Aside>
          </el-menu>
        </el-scrollbar>
      </el-aside>
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

</style>