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

</script>

<template>
  <div>
    <div style="margin-top: 10px"><IconHeaderLogo /><span>实验室</span></div>
  </div>
  <div class="toolbar">
    <el-dropdown :hide-on-click="false" @command="dropdownCommand">
      <span>{{authenticateStore.principal.me.username}}</span>
      <el-icon style="margin-right: 8px; margin-top: 1px"><arrow-down /></el-icon>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item command="me">个人中心</el-dropdown-item>
          <el-dropdown-item command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<style scoped>

</style>