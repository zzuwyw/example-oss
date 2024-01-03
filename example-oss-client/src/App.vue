<script setup>
import {usePrincipalStore} from "@/stores/principal.js";
import router from "@/router/index.js";
import {get} from "@/net/index.js";

const principalStore = usePrincipalStore();

if (!principalStore.principal.userDetail) {
  get('/auth/getPrincipal', (data) => {
    principalStore.principal.userDetail = data;
    router.push("/home");
  }, () => {
    principalStore.principal.userDetail = null;
    router.push("/");
  })
}

</script>

<template>
  <router-view/>
</template>

<style scoped>

</style>
