<script setup>

import IconAsideLogo from "@/components/icons/IconAsideLogo.vue";

// 接收父组件传递的参数
const props = defineProps({
  isCollapse: {
    require: false, // true显示，false隐藏
    type: Boolean
  }
});

</script>

<template>
  <a class="aside-logo">
    <IconAsideLogo />
    <span class="aside-logo-text" v-show="!props.isCollapse">实验室</span>
  </a>
</template>

<style scoped>
.aside-logo {
  display: flex;
  align-items: center;
  text-align: center;
  .aside-logo-text {
    padding-left: 6px;
    margin-inline-start: 8px;
    color: rgba(0, 0, 0, 0.88);;
    font-size: 18px;
    font-weight: 500;
    cursor: pointer;
  }
}
</style>