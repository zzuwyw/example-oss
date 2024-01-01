<script setup>
import Logo from "@/components/layout/aside/Logo.vue";
import Menu from "@/components/layout/aside/Menu.vue";
import useMenuStore from "@/stores/menu.js";

const menuStore = useMenuStore();
</script>

<template>
  <div class="aside">
    <Logo :isCollapse="menuStore.isCollapse" class="aside-logo" />
    <Menu class="aside-menu" />
  </div>
</template>

<style scoped>
.aside {
  background-color: #33a06f;
  .aside-logo {
    display: flex;
    align-items: center;
    text-align: center;
    height: 44px;
    line-height: 220px;
    padding-left: 15px;
  }
  .aside-menu {
    height: calc(100vh - 44px);
  }
}



</style>