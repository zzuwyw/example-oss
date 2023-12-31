<script setup>
import {ArrowDown} from "@element-plus/icons-vue";

import {get} from "@/net/index.js";
import router from "@/router/index.js";
import {principalStore} from "@/stores/me.js";
import IconHeaderLogo from "@/components/icons/IconHeaderLogo.vue";

const authenticateStore = principalStore();

const logout = () => {
  get('/auth/logout', () => {
    authenticateStore.principal.me = null;
    router.replace('/')
  })
}

const dropdownCommand = (command) => {
  switch (command) {
    case "me":
      router.push("/system/personage");
      break;
    case "logout":
      logout();
      break;
  }
};

import { ref, onBeforeUnmount } from "vue";
import {useDebounceFn} from "@vueuse/core";
/** 监听窗口大小变化，进行是否显示 */
const showTool = ref(true); // 是否显示
const screenWidth = ref(0);
const listeningWindow = useDebounceFn(() => {
  screenWidth.value = document.body.clientWidth;
  showTool.value = screenWidth.value >= 865;
}, 100);
window.addEventListener("resize", listeningWindow, false);
onBeforeUnmount(() => {
  window.removeEventListener("resize", listeningWindow);
});

</script>

<template>
  <div class="header">
    <div class="header-title">
      <IconHeaderLogo class="header-logo"/>
      <span class="header-logo-text">实验室</span>
    </div>
    <div class="header-body">
      <div class="header-left"></div>
      <div class="header-right">
        <el-dropdown :hide-on-click="false" @command="dropdownCommand">
          <div class="user-dropdown">
            <span>{{authenticateStore.principal.me.username}}</span>
            <el-icon style="margin-right: 8px; margin-top: 1px"><arrow-down /></el-icon>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="me">个人中心</el-dropdown-item>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<style>

</style>

<style lang="scss" scoped>

.header {
  height: 52px;
  display: flex;
  overflow: hidden;
  background-color: antiquewhite;

  .header-title {
    width: 220px;
    background-color: pink;
    .header-logo {
    }
    .header-logo-text {
      padding-left: 10px;
      font-size: 20px;
      font-weight: bold;
    }
  }

  .header-body {
    flex: 1; // 剩余空间全占满
    display: flex;
    justify-content: space-between;

    .header-left {
      display: flex;
      align-items: center;
      overflow: hidden;
      white-space: nowrap;
    }
    .header-right {
      display: flex;
      align-items: center;
    }
    .user-dropdown {
      cursor: pointer;
      color: var(--el-color-primary);
      display: flex;
      align-items: center;
      white-space: nowrap;
      outline: none; // 去除伪元素
    }
  }
}

</style>