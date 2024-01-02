<script setup>
import Menu from "@/components/layout/aside/Menu.vue";
</script>

<template>
  <div class="aside">
    <el-scrollbar class="aside-scrollbar">
      <Menu class="aside-menu" />
    </el-scrollbar>
  </div>
</template>

<style scoped>
.aside {
  background-color: transparent;
  border-inline-end: 1px solid rgba(5, 5, 5, 0.06);
  .aside-scrollbar {
    padding-top: 6px;
    padding-left: 6px;
    padding-right: 6px;
    .aside-menu {
      height: calc(100vh - 44px);
    }
  }
}

</style>