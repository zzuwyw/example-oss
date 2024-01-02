<script setup>
import {get} from "@/net/index.js";
import router from "@/router/index.js";
import {usePrincipalStore} from "@/stores/principal.js";

const principalStore = usePrincipalStore();

const logout = () => {
  get('/auth/logout', () => {
    principalStore.principal.userDetail = null;
    router.push('/')
  })
}

</script>

<template>
  <main>
    <div>欢迎{{principalStore.principal.userDetail ? principalStore.principal.userDetail.username : ''}}进入首页</div>
    <div>
      <el-button @click="logout()" type="primary">退出登录</el-button>
    </div>
  </main>
</template>
