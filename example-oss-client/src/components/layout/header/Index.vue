<script setup>
import Logo from "@/components/layout/header/Logo.vue";
import BreadCrumb from "@/components/layout/header/BreadCrumb.vue";
import User from "@/components/layout/header/User.vue";
</script>

<template>
  <div class="header">
    <div class="header-left">
      <Logo class="aside-logo" />
      <BreadCrumb />
    </div>
    <div class="header-right">
      <User />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.header {
  display: flex;
  justify-content: space-between;
  height: 44px;

  .header-left {
    display: flex;
    align-items: center;
    overflow: hidden;
    white-space: nowrap;
  }

  .header-right {
    display: flex;
    align-items: center;
  }
}
</style>