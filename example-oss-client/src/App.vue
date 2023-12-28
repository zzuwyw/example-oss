<script setup>
import {principalStore} from "@/stores/me.js";
import router from "@/router/index.js";
import axios from "axios";

const store = principalStore();

if (!store.principal.me) {
  // 获取已登录用户信息
  axios.get('/auth/me',{
    withCredentials: true
  }).then(({data}) => {
    if (data.code === 200) {
      store.principal.me = data.data;
      router.push("/index");
    } else {
      store.principal.me = null;
      router.push("/");
    }
  })
}

</script>

<template>
  <router-view/>
</template>

<style scoped>

</style>
