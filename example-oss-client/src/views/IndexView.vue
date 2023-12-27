<script setup>
import {get} from "@/net/index.js";
import router from "@/router/index.js";

const logout = () => {
  get('/auth/logout', (data) => {
    console.log('logout data:' + data)
    router.push('/')
  })
}
</script>

<template>
  <main>
    <div>欢迎进入首页</div>
    <div>
      <el-button @click="logout()" type="primary">退出登录</el-button>
    </div>
  </main>
</template>
