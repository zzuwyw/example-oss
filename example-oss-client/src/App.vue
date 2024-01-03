<script setup>
import {usePrincipalStore} from "@/stores/principal.js";
import router from "@/router/index.js";
import axios from "axios";
import {ElMessage} from "element-plus";
import {get} from "@/net/index.js";

const principalStore = usePrincipalStore();

if (!principalStore.principal.userDetail) {
  get('/auth/getPrincipal', (data) => {
    if (data.code === 200) {
      principalStore.principal.userDetail = data.data;
      router.push("/index");
    } else {
      principalStore.principal.userDetail = null;
      router.push("/");
    }
  })
  /*
  // 获取已登录用户信息
  axios.get('/auth/getPrincipal',{
    withCredentials: true
  }).then(({data}) => {
    if (data.code === 200) {
      principalStore.principal.userDetail = data.data;
      router.push("/index");
    } else {
      principalStore.principal.userDetail = null;
      router.push("/");
    }
  }).catch((error) => {
    let errorMessage = error.response.data.message;
    if (errorMessage !== '用户未认证') {
      ElMessage.warning(error.response.data.message);
    }
  })*/
}

</script>

<template>
  <router-view/>
</template>

<style scoped>

</style>
