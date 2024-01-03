<script setup>
import {ArrowDown, UserFilled, CircleClose} from "@element-plus/icons-vue";

import {get} from "@/net/index.js";
import router from "@/router/index.js";
import {usePrincipalStore} from "@/stores/principal.js";

const principalStore = usePrincipalStore();

const logout = () => {
  get('/auth/logout', () => {
    principalStore.principal.userDetail = null;
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
import IconMe from "@/components/icons/IconMe.vue";
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
        <IconMe />
        <span style="margin-left: 5px">{{principalStore.principal.userDetail ? principalStore.principal.userDetail.username : ''}}</span>
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
    display: flex;
    align-items: center;
    color: rgba(0, 0, 0, 0.6);
    cursor: pointer;
    white-space: nowrap;
    outline: none; // 去除伪元素
    padding: 3px;
    border-radius: 6px;
    user-select: none;

    &:hover {
      background-color: rgba(0, 0, 0, 0.08);
    }
  }
}
.user-info {
  width: 200px;
  height: 50px;
  padding-top: 20px;
  padding-bottom: 10px;
  background-color: RGB(242,242,243);
  border-bottom: 1px solid rgba(170,170,174,0.3);

  .user-nick-name {
    color: rgba(0, 0, 0, 0.95);
    font-size: 16px;
    font-weight: bold;
    padding-left: 20px;
  }
  .user-login-name {
    color: rgba(0, 0, 0, 0.6);
    font-size: 14px;
    padding-left: 20px;
    padding-top: 5px;
  }
}

:global(.dropdown-popper .el-dropdown-menu__item) {
  user-select: none;
}

</style>