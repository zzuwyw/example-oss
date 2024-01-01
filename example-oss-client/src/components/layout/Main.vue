<script setup>

</script>

<template>
  <router-view v-slot="Component, route">
    <component :is="Component"></component>
  </router-view>
</template>

<style scoped>

</style>