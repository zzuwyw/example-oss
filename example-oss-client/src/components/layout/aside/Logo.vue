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
  <div class="aside-logo">
    <IconAsideLogo />
    <span class="aside-logo-text" v-show="!props.isCollapse">实验室</span>
  </div>
</template>

<style scoped>
.aside-logo {
  .aside-logo-text {
    padding-left: 10px;
    color: whitesmoke;
    font-size: 20px;
    font-weight: bold;
  }
}
</style>