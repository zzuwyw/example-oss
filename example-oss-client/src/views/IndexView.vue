<script setup>
import {get} from "@/net/index.js";
import router from "@/router/index.js";
import {principalStore} from "@/stores/me.js";

const store = principalStore();

const logout = () => {
  get('/auth/logout', () => {
    store.principal.me = null;
    router.push('/')
  })
}

</script>

<template>
  <main>
    <div>欢迎{{store.principal.me ? store.principal.me.username : ''}}进入首页</div>
    <div>
      <el-button @click="logout()" type="primary">退出登录</el-button>
    </div>
  </main>
</template>
