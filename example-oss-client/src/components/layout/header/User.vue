<script setup>
import {ArrowDown, UserFilled, CircleClose} from "@element-plus/icons-vue";

import {get} from "@/net/index.js";
import router from "@/router/index.js";
import {principalStore} from "@/stores/me.js";

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
      router.push("/me");
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
  <div class="user">
    <el-dropdown trigger="click" :hide-on-click="false" popper-class="dropdown-popper" @command="dropdownCommand">
      <div class="user-dropdown">
        <el-avatar :size="26" :icon="UserFilled"></el-avatar>
        <span>{{authenticateStore.principal.me.username}}</span>
        <el-icon style="margin-right: 8px; margin-top: 1px"><arrow-down /></el-icon>
      </div>
      <template #dropdown>
        <div class="user-info">
          <div class="user-nick-name">超级管理员</div>
          <div class="user-login-name">admin</div>
        </div>
        <el-dropdown-menu>
          <el-dropdown-item command="me" :icon="UserFilled">个人中心</el-dropdown-item>
          <el-dropdown-item command="logout" :icon="CircleClose">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<style lang="scss" scoped>

.user {
  display: flex;
  align-items: center;

  .user-dropdown {
    cursor: pointer;
    color: var(--el-color-primary);
    display: flex;
    align-items: center;
    white-space: nowrap;
    outline: none; // 去除伪元素
  }
}
.user-info {
  width: 100%;
  height: 50px;
  padding-top: 20px;
  padding-bottom: 10px;
  background-color: whitesmoke;
  border-bottom: lightgrey 1px solid;
  .user-nick-name {
    padding-left: 20px;
    font-size: 16px;
    font-weight: bold;
  }
  .user-login-name {
    padding-left: 20px;
    padding-top: 5px;
    color: gray;
    font-size: 14px;
  }
}

:global(.dropdown-popper .el-dropdown-menu__item) {
  width: 200px;
}
</style>