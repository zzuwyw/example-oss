<script setup>
import IconMenuExpand from "@/components/icons/IconMenuExpand.vue";
import IconMenuCollapse from "@/components/icons/IconMenuCollapse.vue";
import useMenuStore from "@/stores/menu.js";

const menuStore = useMenuStore();

const changeCollapseState = () => {
  menuStore.isCollapse = menuStore.setIsCollapse(!menuStore.isCollapse);
};
</script>

<template>
  <el-icon class="collapse-icon" size="20px" @click="changeCollapseState()">
    <IconMenuExpand width="20px" height="20px" v-if="!menuStore.isCollapse" />
    <IconMenuCollapse width="20px" height="20px" v-if="menuStore.isCollapse" />
  </el-icon>
</template>

<style scoped>
.collapse-icon {
  color: #42b883;
  &:hover {
    color: #298059;
    cursor: pointer;
  }
}
</style>