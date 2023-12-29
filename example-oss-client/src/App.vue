<script setup>
import {principalStore} from "@/stores/me.js";
import router from "@/router/index.js";
import axios from "axios";
import {ElMessage} from "element-plus";

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
  }).catch((error) => {
    let errorMessage = error.response.data.message;
    if (errorMessage !== '用户未认证') {
      ElMessage.warning(error.response.data.message);
    }
  })
}

</script>

<template>
  <router-view/>
</template>

<style scoped>

</style>
