<script setup>

</script>

<template>
  <div style="width: 100vw;height: 100vh;overflow: hidden;display: flex">
    <div id="bg" style="flex: 1;background-color: blueviolet">
      <el-image src="./src/assets/bg.jpg" style="width: 100%;height: 100%"/>
    </div>
    <div class="welcome-title">
      <div style="font-size: 30px;font-weight: bold">欢迎来到实验室</div>
      <div>在这里可以做各种实验，感兴趣的赶快来</div>
    </div>
    <div style="width: 30%;min-width: 400px;background-color: white">
      <router-view/>
    </div>
  </div>
</template>

<style scoped>
.welcome-title {
  position: absolute;
  bottom: 80px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;
}
</style>